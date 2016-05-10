package si.fri.t15.login.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.Result_Checkup;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import org.springframework.transaction.annotation.Transactional;

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
		
		if(userSession.getData() == null){
			return "redirect:/createProfile";
		}
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		
		String userType = "user";
		User user = em.merge(userSession);
		PatientData pdata = em.merge(userSession.getSelectedPatient());		
		String fname = pdata.getFirst_name();
		String lname = pdata.getLast_name();
		
		//user == patient
		DoctorData personal_doctor = pdata.getDoctor();
		List <Appointment> appointments = pdata.getAppointments();
		List <Checkup> checkups = pdata.getCheckups();
		
		List<Disease> diseases = pdata.getDiseases();
		//List<Medicine> medicines = pdata.getMedicines();
		List<Diet> diets = pdata.getDiets();
		List<Result_Checkup> results = pdata.getResults();
		List<PatientData> caretaker = (List<PatientData>) pdata.getCaretaker();
		
		model.addAttribute("fname", fname);
		model.addAttribute("lname", lname);
		model.addAttribute("doctor", personal_doctor);
		
		model.addAttribute("checkups", checkups);		
		model.addAttribute("diseases", diseases); 
		//model.addAttribute("medicines", medicines); 
		model.addAttribute("diets", diets); 
		model.addAttribute("results", results); 
		
		model.addAttribute("appointments", appointments); 
		model.addAttribute("caretaker", caretaker); 
				
		//Naloži lazy podatke
		if(user.getData().getClass().equals(PatientData.class)){
			((PatientData)user.getData()).getPatients();
		}
		
		if(user.getUserRoles().contains("ROLE_ADMIN")) {
			userType = "admin";
		}
		
		model.addAttribute("usertype", userType);
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("p", pdata);
		model.addAttribute("page", "home");
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "NADZORNA PLOŠČA MEDIKO");
		model.addAttribute("user", user);
		
		return "home";
	}	
}