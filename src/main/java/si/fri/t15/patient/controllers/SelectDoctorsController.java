package si.fri.t15.patient.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.ChangePasswordForm;
import si.fri.t15.validators.SelectDoctorForm;
import si.fri.t15.validators.SelectDoctorValidator;

@Controller
public class SelectDoctorsController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	SelectDoctorValidator selectDoctorValdiator;
	
	@InitBinder("command")
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(selectDoctorValdiator);
	}
	
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
		int selectedDentist = (userSession.getSelectedPatient().getDentist() != null)?userSession.getSelectedPatient().getDentist().getId():-1; //need fix
		
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
	
	@Transactional
	@RequestMapping(value = "/doctors/select", method=RequestMethod.POST)
	public ModelAndView selectDoctors(Model model, @ModelAttribute("command") @Valid SelectDoctorForm command,BindingResult result, HttpServletRequest request, @AuthenticationPrincipal User userSession){
		
		String userType = "user";
		User user = em.merge(userSession);
		if(user.getUserRoles().contains("ROLE_ADMIN")) {
			userType = "admin";
		}
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		
		PatientData selectedPatient = userSession.getSelectedPatient();
		selectedPatient = em.merge(selectedPatient);
		
		//Get selected user doctors Ids
		int selectedDoctor = (userSession.getSelectedPatient().getDoctor() != null)?userSession.getSelectedPatient().getDoctor().getId():-1;
		int selectedDentist = (userSession.getSelectedPatient().getDentist() != null)?userSession.getSelectedPatient().getDentist().getId():-1; //need fix
		
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
		model.addAttribute("selectedPatient", selectedPatient);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "doctors");
		model.addAttribute("subpage", "selectDoctors");	
		//Page variables
		model.addAttribute("title", "Izberi zdravnike");

		
		//Check if doctor can take another patient
		if(selectedDoctor != command.getDoctor()){
			Query doctorQuery = em.createNamedQuery("DoctorData.GetDoctorById");
			doctorQuery.setParameter("id", command.getDoctor());
			DoctorData newSelectedDoctor = (DoctorData)doctorQuery.getSingleResult();
			if(newSelectedDoctor != null && newSelectedDoctor.getType().equals("Doctor") && newSelectedDoctor.getPatients().size() < newSelectedDoctor.getMaxPatients()){
				//Set doctor
				selectedPatient.setDoctor(newSelectedDoctor);
			}else{
				result.rejectValue("doctor", "field.format", "Pri izbiri zdravnika je prišlo do napake!");
			}
		}
		
		if(selectedDentist != command.getDentist()){
			Query doctorQuery = em.createNamedQuery("DoctorData.GetDoctorById");
			doctorQuery.setParameter("id", command.getDentist());
			DoctorData newSelectedDentist = (DoctorData)doctorQuery.getSingleResult();
			if(newSelectedDentist != null && newSelectedDentist.getType().equals("Dentist") && newSelectedDentist.getPatients().size() < newSelectedDentist.getMaxPatients()){
				//Set dentist
				selectedPatient.setDentist(newSelectedDentist);
			}else{
				result.rejectValue("dentist", "field.format", "Pri izbiri zobozdravnika je prišlo do napake!");
			}
		}
		
		em.persist(selectedPatient);
		userSession.setSelectedPatient(selectedPatient);
		
		//
		if(result.hasErrors()){
			return new ModelAndView("selectDoctors");
		}
		
		
		return new ModelAndView("redirect:/doctors/select");
	}
}
