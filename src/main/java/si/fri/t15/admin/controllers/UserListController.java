package si.fri.t15.admin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;

@Controller
public class UserListController extends ControllerBase {
	
	
	@RequestMapping(value = "/admin/users/notcompleted",  method=RequestMethod.GET)
	public ModelAndView createMedicalWorkerGET(Model model, HttpServletRequest request) {
		
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "userList");
		model.addAttribute("subpage", "notCompleted");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Seznam nedokončanih postopkov registracije");
		
		return new ModelAndView("usersListNotCompleted");
	}
}
