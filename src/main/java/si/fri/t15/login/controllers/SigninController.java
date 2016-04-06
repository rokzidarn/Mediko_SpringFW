package si.fri.t15.login.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class SigninController extends ControllerBase {
	
	@RequestMapping(value = "index/signin")
	public String signin(Model model, HttpServletRequest request) {
		model.addAttribute("signin", "signin");
		model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Prijava");
		return "signin";
	}
	
}