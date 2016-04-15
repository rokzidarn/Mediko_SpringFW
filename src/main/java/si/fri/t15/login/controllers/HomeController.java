package si.fri.t15.login.controllers;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.user.User;

@Controller
public class HomeController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/dashboard")
	public String home(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//Side menu variables
		String userType = "user";
		user = em.merge(user);
		if(user.getUserRoles().contains("ROLE_ADMIN")) {
			userType = "admin";
		}
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "home");
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "Prijava");
		model.addAttribute("user", user);
		return "home";
	}
}
