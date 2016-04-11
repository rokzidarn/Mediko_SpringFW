package si.fri.t15.admin.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.web.csrf.CsrfToken;
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
import si.fri.t15.validators.CreateMedicalWorkerForm;

@Controller
public class CreateMedicalWorkerController extends ControllerBase{
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator);
	}
 
	@Resource(name="createMedicalWorkerValidator")
	Validator validator;
	
	@RequestMapping(value = "/admin/createMedicalWorker")
	public ModelAndView createMedicalWorker(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//Side menu variables
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "Dodaj osebje");
		
		if (result.hasErrors()) {
			return new ModelAndView("createMedicalWorker");
		}
		
		//send email to confirm, don't show home/home!
		
		return new ModelAndView("createMedicalWorker");
	}
}
