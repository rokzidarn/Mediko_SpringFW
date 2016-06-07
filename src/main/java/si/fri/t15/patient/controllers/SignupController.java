package si.fri.t15.patient.controllers;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.web.WebAttributes;
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
import si.fri.t15.base.models.UserData;
import si.fri.t15.dao.UserRepository;
import si.fri.t15.models.PO_Box;
import si.fri.t15.models.UserRole;
import si.fri.t15.models.user.EmergencyContactData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.PatientProfileForm;
import si.fri.t15.validators.SignUpForm;

@Controller
public class SignupController extends ControllerBase {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserRepository userRepo;
	
	@Value("${proxy.realPath:#{null}}")
	private String domain;
	
	
	@InitBinder("command")
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator);
	}
 
	@Resource(name="signUpValidator")
	Validator validator;
	

	
	@RequestMapping(value = "/signup", method=RequestMethod.GET)
	public ModelAndView signupGET(Model model, HttpServletRequest request) {
		
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes",poBoxes);
		model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
		
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Ustvari Račun");
		model.addAttribute("page", "register");
		
		return new ModelAndView("signup");
	}
	
	@Transactional
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public ModelAndView signupPOST(Model model, @ModelAttribute("command") @Valid SignUpForm command, BindingResult result,
			HttpServletRequest request) {
		
		if (result.hasErrors()) {
			model.addAttribute("page", "register");
			model.addAttribute("title", "Ustvari Račun");
			Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
			List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
			model.addAttribute("po_boxes",poBoxes);
			model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
			
			return new ModelAndView("signup");
		}
		
		User newUser = new User();
		
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
		
		if (command.containsProfileData()) {
			EmergencyContactData eData = new EmergencyContactData();
			eData.setAddress(command.getContactAddress());
			eData.setFirst_name(command.getContactFirstName());
			eData.setLast_name(command.getContactLastName());
			eData.setPhoneNumber(command.getContactPhoneNumber());
			eData.setRelationshipType(command.getContactRelationship());

			em.persist(eData);

			PatientData pData = new PatientData();
			pData.setAddress(command.getAddress());
			pData.setBirth_date(Date.valueOf(command.getBirth()));
			pData.setCardNumber(command.getCardNumber());
			pData.setFirst_name(command.getFirstName());
			pData.setLast_name(command.getLastName());
			pData.setPhoneNumber(command.getPhoneNumber());
			pData.setPo_number(command.getPobox());
			pData.setSex(command.getSex());

			pData.setEmergencyContactData(eData);

			em.persist(pData);
			newUser.setData(pData);
		}
		
		//save user
		em.persist(newUser);
		
		String tString = null;
		String cpath = request.getContextPath().toString();
		if (domain != null) {
			tString = domain + cpath;
		}
		else {
			domain = request.getRequestURL().toString();
			tString = domain.substring(0, domain.indexOf(cpath) + cpath.length());
		}
		
		signUpConfirmationMail(newUser, tString, activationToken);
		
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "auth.message.confirm_mail");
		
		return new ModelAndView("redirect:/login");
	}
	
	@Transactional
	@RequestMapping("/confirm")
	public String confirmRegistration(Model model, HttpServletRequest request, @RequestParam String token) {
		User user = userRepo.findByPasswordResetToken(token);
		if(user!=null) {
			user.setEnabled(true);
			user.setPasswordResetToken(null);
			em.merge(user);
			return "redirect:/login?activationSuccess";
		}
		
		return "redirect:/login?activationError";
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