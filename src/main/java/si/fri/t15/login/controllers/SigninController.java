package si.fri.t15.login.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.validators.SignInForm;

@Controller
public class SigninController extends ControllerBase {
	
	@RequestMapping(value = "index/signin", method=RequestMethod.GET)
	public String signin(Model model, HttpServletRequest request) {
		model.addAttribute("signin", "signin");
		model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Prijava");
		return "signin";
	}
	
	@RequestMapping(value = "index/signin", method=RequestMethod.POST)
	public String validateSignIn(@Valid SignInForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "signin";
        }

        return "redirect: mediko_dev/../../home";
	}
	
}