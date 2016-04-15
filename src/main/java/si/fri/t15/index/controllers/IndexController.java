package si.fri.t15.index.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class IndexController extends ControllerBase{
	
	@RequestMapping(value = "/")
	public String index(Model model, HttpServletRequest request) {
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("page", "index");
		model.addAttribute("user", "none");
		model.addAttribute("title", "MEDIKO");

		return "index";
	}
}
