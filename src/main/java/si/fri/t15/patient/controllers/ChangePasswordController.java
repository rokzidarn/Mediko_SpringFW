package si.fri.t15.patient.controllers;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.models.user.User.UserType;
import si.fri.t15.validators.ChangePasswordForm;
import si.fri.t15.validators.ChangePasswordValidator;

@Controller
public class ChangePasswordController extends ControllerBase {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ChangePasswordValidator changePasswordValidator;
	
	@InitBinder("command")
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(changePasswordValidator);
	}
	
	@Transactional
	@RequestMapping(value = "/settings/password", method=RequestMethod.GET)
	public String changePasswordController(Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession)
	{
		String userType = "user";
		User user = em.merge(userSession);
		if(UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		model.addAttribute("user",user);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "settings");
		model.addAttribute("subpage", "changePassword");	
		//Page variables
		model.addAttribute("title", "Spremeni geslo");
		
		return "changePassword";
	}
	
	@Transactional
	@RequestMapping(value = "/settings/password", method=RequestMethod.POST)
	public ModelAndView createPatientPOST(Model model, @ModelAttribute("command") @Valid ChangePasswordForm command,BindingResult result, HttpServletRequest request, @AuthenticationPrincipal User userSession) {
		
		String userType = "user";
		User user = em.merge(userSession);
		if(UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		model.addAttribute("user",user);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "settings");
		model.addAttribute("subpage", "changePassword");	
		//Page variables
		model.addAttribute("title", "Spremeni geslo");
		
		//Check if same mail
		if(!user.getUsername().equals(command.getEmail())){
			result.rejectValue("email", "field.format", "Email se ne ujema");
		}
		
		if (result.hasErrors()) {
			return new ModelAndView("changePassword");
		}
		
		//No errors
		user.setPassword(passwordEncoder.encode(command.getPassword()));
		em.persist(user);
		//userSession = user;
		
		userSession.setPassword(passwordEncoder.encode(command.getPassword()));
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,auth.getCredentials()));
		
		return new ModelAndView("redirect:/dashboard");
		
	}
}
