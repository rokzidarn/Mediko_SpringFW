package si.fri.t15.patient.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.validators.SignUpForm;

@Controller
public class SignupController extends ControllerBase {
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator);
	}
 
	@Resource(name="signUpValidator")
	Validator validator;
	
	@RequestMapping(value = "/patient/signup")
	public ModelAndView signup(Model model, @ModelAttribute("command") @Valid SignUpForm command,
			BindingResult result, HttpServletRequest request) {
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Ustvari Račun");
		model.addAttribute("user","none");
		model.addAttribute("page", "register");
		
		if (result.hasErrors()) {
			return new ModelAndView("signup");
		}
		
		//send email to confirm, don't show home/home!
		
		return new ModelAndView("signup");
	}
	
}