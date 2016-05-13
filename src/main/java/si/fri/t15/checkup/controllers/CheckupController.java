package si.fri.t15.checkup.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class CheckupController extends ControllerBase {
	
	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}")
	public String checkup(@PathVariable("id") int id,Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession) {		
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		
		//Side menu variables
		String userType = "user";
		User user = em.merge(userSession);
		PatientData p = em.merge(userSession.getSelectedPatient());
		
		Checkup curr = null;
		
		for(Checkup c : p.getCheckups()){
			if(c.getId() == id){
				curr = c;
				break;
			}
		}
		
		DoctorData d = curr.getDoctor();
				
		model.addAttribute("idc", id); 
		model.addAttribute("instructions", curr.getInstructions());
		model.addAttribute("reason", curr.getReason());	
		model.addAttribute("date", curr.getAppointment().getDate());	
		model.addAttribute("pdata", p); 
		model.addAttribute("ddata", d); 
				
		model.addAttribute("diseases", curr.getDiseases()); 
		model.addAttribute("diets", curr.getDiets()); 
		model.addAttribute("medicines", curr.getMedicines());
		
		//Side menu variables
		model.addAttribute("usertype", userType);
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("page", "home");
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "PREGLED");
		model.addAttribute("user", user);
		return "checkup";
	}
}
