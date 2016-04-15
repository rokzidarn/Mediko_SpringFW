package si.fri.t15.login.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class SigninController extends ControllerBase {
	
	@RequestMapping(value = "index/signin", method=RequestMethod.GET)
	public String signin(Model model, HttpServletRequest request) {
		model.addAttribute("signin", "signin");
		model.addAttribute("page", "login");
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Prijava");
		return "signin";
	}
	
}