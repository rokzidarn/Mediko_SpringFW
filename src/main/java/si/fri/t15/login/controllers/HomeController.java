package si.fri.t15.login.controllers;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.base.models.UserData;
import si.fri.t15.models.PO_Box;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;

@Controller
public class HomeController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@RequestMapping(value = "/dashboard/patient/{id}")
	@Transactional
	public String setSelectedPatient(@PathVariable("id") int patientId, Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		
		if(user.getData().getId() == patientId){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}else{
			
			for(PatientData patient : ((PatientData)user.getData()).getPatients()){
				if(patient.getId() == patientId){
					userSession.setSelectedPatient(patient);
					break;
				}
			}
		}
			
		
		return "redirect:/dashboard";
	}
	
	@Transactional
	@RequestMapping(value = "/dashboard")
	public String home(Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//REDIRECT ČE NIMA PATIENT DATA
		if(userSession.getData() == null){
			return "redirect:/createProfile";
		}
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		
		//Side menu variables
		String userType = "user";
		User user = em.merge(userSession);
		PatientData p = em.merge(userSession.getSelectedPatient());
		
		Hibernate.initialize(p.getPo_box());
		Hibernate.initialize(p.getDoctor());
		Hibernate.initialize(p.getDoctor().getMedical_center());
		Hibernate.initialize(p.getDoctor().getNurses());
		
		//Naloži lazy podatke
		if(user.getData().getClass().equals(PatientData.class)){
			((PatientData)user.getData()).getPatients();
		}
		
		if(user.getUserRoles().contains("ROLE_ADMIN")) {
			userType = "admin";
		}
		model.addAttribute("usertype", userType);
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("p", p);
		model.addAttribute("page", "home");
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "NADZORNA PLOŠČA MEDIKO");
		model.addAttribute("user", user);
		return "home";
	}
}
