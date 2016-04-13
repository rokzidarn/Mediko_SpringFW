package si.fri.t15.patient.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.validators.SignUpForm;

@Controller
public class CreatePatientController extends ControllerBase{
	
	@RequestMapping(value = "patient/createPatient", method=RequestMethod.GET)
	public String createPatient(Model model, HttpServletRequest request) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//Side menu variables
		model.addAttribute("usertype", "user");
		model.addAttribute("page", "patient");
		model.addAttribute("subpage", "createPatient");	
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "Dodaj pacienta");
		return "createPatient";
	}

}
