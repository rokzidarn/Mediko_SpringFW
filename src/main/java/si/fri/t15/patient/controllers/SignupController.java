package si.fri.t15.patient.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.UserRole;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.SignUpForm;

@Controller
public class SignupController extends ControllerBase {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PersistenceContext
    private EntityManager em;
	
	
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
		newUser.setEnabled(true); //false for mail confirmation
		
		Query userRoleQuery = em.createNamedQuery("UserRole.findByRole");
		userRoleQuery.setParameter("role", "ROLE_USER");
		List<UserRole> res = (List<UserRole>)userRoleQuery.getResultList();
		//UserRole userRole = (UserRole)userRoleQuery.getSingleResult();
		UserRole userRole = null;
		if(res.isEmpty()){
			userRole = new UserRole();
			userRole.setRole("ROLE_USER");
		}else{
			userRole = res.get(0);
		}
		//Save user role
		em.persist(userRole);
		
		//Set users userroles
		Set<UserRole> userRoles = new HashSet<>(0);
		userRoles.add(userRole);
		newUser.setUserRoles(userRoles);
		
		//save user
		em.persist(newUser);
		//send email to confirm, don't show home/home!
		
		return new ModelAndView("redirect:/index/signin");
	}
	
}