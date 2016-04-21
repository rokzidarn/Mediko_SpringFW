package si.fri.t15.admin.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import si.fri.t15.models.UserRole;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.NurseData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.CreateMedicalWorkerForm;

@Controller
public class CreateMedicalWorkerController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator);
	}
 
	@Resource(name="createMedicalWorkerValidator")
	Validator validator;
	
	@Transactional
	@RequestMapping(value = "/admin/createMedicalWorker",  method=RequestMethod.POST)
	public ModelAndView createMedicalWorkerPOST(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		
		/*
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Dodaj osebje");
		*/
		
		if (result.hasErrors()) {
			return new ModelAndView("createMedicalWorker");
		}
		
		User user = new User();
		user.setUsername(command.getEmail());
		user.setPassword(passwordEncoder.encode(command.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		Set<UserRole> userRoles = new HashSet<>();
		
		Query userRoleQuery = em.createNamedQuery("UserRole.findByRole");
		userRoleQuery.setParameter("role", "ROLE_USER");
		userRoles.add((UserRole) userRoleQuery.getSingleResult());
		
		userRoleQuery.setParameter("role", "ROLE_" + command.getType().toUpperCase());

		userRoles.add((UserRole) userRoleQuery.getSingleResult());
		user.setUserRoles(userRoles);
		
		em.persist(user);
		
		return new ModelAndView("redirect:/admin/createMedicalWorker?successfulRegistration=true");
	}
	
	@RequestMapping(value = "/admin/createMedicalWorker",  method=RequestMethod.GET)
	public ModelAndView createMedicalWorkerGET(Model model, HttpServletRequest request, @RequestParam(required = false) boolean successfulRegistration) {
		
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Dodaj osebje");
		if(successfulRegistration) {
			model.addAttribute("registrationSuccess", "admin.user_registration_successful");
		}
		
		return new ModelAndView("createMedicalWorker");
	}
}
