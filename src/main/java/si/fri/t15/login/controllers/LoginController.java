package si.fri.t15.login.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class LoginController extends ControllerBase {
	
	@RequestMapping(value = "/login")
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("login", "login");
		model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		model.addAttribute("trans", getTranslation("login.title", request));
		if(request.getParameterMap().containsKey("error")){
			model.addAttribute("error", "error");
		}
		else if (request.getParameterMap().containsKey("logout")){
			model.addAttribute("logout", "logout");
		}
		return "login";
	}
	
}