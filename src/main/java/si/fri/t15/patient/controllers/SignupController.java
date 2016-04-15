package si.fri.t15.patient.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.dao.UserRepository;
import si.fri.t15.models.UserRole;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.SignUpForm;

@Controller
public class SignupController extends ControllerBase {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	UserRepository userRepo;
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator);
	}
 
	@Resource(name="signUpValidator")
	Validator validator;
	

	
	@RequestMapping(value = "/signup", method=RequestMethod.GET)
	public ModelAndView signupGET(Model model, HttpServletRequest request) {
		
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Ustvari Račun");
		model.addAttribute("user","none");
		model.addAttribute("page", "register");
		
		return new ModelAndView("signup");
	}
	
	@Transactional
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public ModelAndView signupPOST(Model model, @ModelAttribute("command") @Valid SignUpForm command,
			BindingResult result, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			return new ModelAndView("signup");
		}
		
		User newUser = new User();
		
		//newUser.setEmail("DELETE THIS ROW"); //Delete, ko se bo updejtal JPA
		newUser.setUsername(command.getUsername());
		newUser.setPassword(passwordEncoder.encode(command.getPassword()));
		newUser.setAccountNonExpired(true);
		newUser.setAccountNonLocked(true);
		newUser.setCredentialsNonExpired(true);
		newUser.setEnabled(false); //false for mail confirmation
		
		Query userRoleQuery = em.createNamedQuery("UserRole.findByRole");
		userRoleQuery.setParameter("role", "ROLE_USER");
		List<UserRole> res = (List<UserRole>)userRoleQuery.getResultList();
		//UserRole userRole = (UserRole)userRoleQuery.getSingleResult();
		UserRole userRole = null;
		if(res.isEmpty()){
			userRole = new UserRole();
			userRole.setRole("ROLE_USER");
			//Save user role
			em.persist(userRole);
		}else{
			userRole = res.get(0);
		}
		
		//Set users userroles
		Set<UserRole> userRoles = new HashSet<>(0);
		userRoles.add(userRole);
		newUser.setUserRoles(userRoles);
		
		String activationToken = UUID.randomUUID().toString().substring(0, 15); 
		newUser.setPasswordResetToken(activationToken);
		
		//save user
		em.persist(newUser);
		
		String domain = request.getRequestURL().toString();
		String cpath = request.getContextPath().toString();

		String tString = domain.substring(0, domain.indexOf(cpath) + cpath.length());
		
		signUpConfirmationMail(newUser, tString, activationToken);
		
		return new ModelAndView("redirect:/index/signin");
	}
	
	@Transactional
	@RequestMapping("/confirm")
	public String confirmRegistration(Model model, HttpServletRequest request, @RequestParam String token) {
		User user = userRepo.findByPasswordResetToken(token);
		if(user!=null) {
			user.setEnabled(true);
			user.setPasswordResetToken(null);
			em.merge(user);
			return "redirect:/index/signin?activationSuccess";
		}
		
		return "redirect:/index/signin?activationError";
	}
	
	private void signUpConfirmationMail(User user, String URL, String activationToken) {
		MimeMessage mail = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(user.getUsername());
			helper.setFrom("mediko@sladic.si");
			helper.setSubject("Potrditvena povezava za registracijo");
			helper.setText("Prosimo, kliknite na povezavo, da zaključite z registracijo\n"
					+ "in aktivirate svoj račun v sistemu Mediko.\n"
					+ URL + "/confirm?token=" + activationToken);
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
		}
		mailSender.send(mail);
	}
}