package si.fri.t15.patient.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class SignupController extends ControllerBase {
	
	@RequestMapping(value = "/patient/signup")
	public String signup(Model model, HttpServletRequest request) {
		return "patient";
	}
	
}