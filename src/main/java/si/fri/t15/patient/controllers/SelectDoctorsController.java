package si.fri.t15.patient.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;

@Controller
public class SelectDoctorsController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/doctors/select", method=RequestMethod.GET)
	public String selectDoctors(Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession)
	{
		String userType = "user";
		User user = em.merge(userSession);
		if(user.getUserRoles().contains("ROLE_ADMIN")) {
			userType = "admin";
		}
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		
		//Get selected user doctors Ids
		int selectedDoctor = (userSession.getSelectedPatient().getDoctor() != null)?userSession.getSelectedPatient().getDoctor().getId():-1;
		int selectedDentist = (userSession.getSelectedPatient().getDoctor() != null)?userSession.getSelectedPatient().getDoctor().getId():-1; //need fix
		
		//GET DOCTORS
		Query doctorsQuery = em.createNamedQuery("DoctorData.GetAvailableDoctors");
		doctorsQuery.setParameter("type", "Doctor");
		doctorsQuery.setParameter("users",selectedDoctor); 
		List<DoctorData> doctors = (List<DoctorData>)doctorsQuery.getResultList();
		
		Query dentistsQuery = em.createNamedQuery("DoctorData.GetAvailableDoctors");
		dentistsQuery.setParameter("type", "Dentist");
		dentistsQuery.setParameter("users", selectedDentist);
		List<DoctorData> dentists = (List<DoctorData>)dentistsQuery.getResultList();
		
		
		model.addAttribute("doctors", doctors);
		model.addAttribute("selectedDoctor", selectedDoctor);
		model.addAttribute("dentists", dentists);
		model.addAttribute("selectedDentist",selectedDentist);
		
		model.addAttribute("user",user);
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "doctors");
		model.addAttribute("subpage", "selectDoctors");	
		//Page variables
		model.addAttribute("title", "Izberi zdravnike");
		
		return "selectDoctors";
	}
}
