package si.fri.t15.login.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class HomeController extends ControllerBase{
	
	@RequestMapping(value = "/home")
	public String home(Model model, HttpServletRequest request) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//Side menu variables
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "doctors");
		
		//Page variables
		model.addAttribute("title", "Home");
		return "home";
	}
}
