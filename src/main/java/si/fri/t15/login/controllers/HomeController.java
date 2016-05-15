package si.fri.t15.login.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import si.fri.t15.models.user.User.UserType;

@Controller
public class HomeController extends ControllerBase{
	@Autowired
	EntityManager em;
	
	@RequestMapping(value = "/dashboard/patient/{id}")
	@Transactional
	public String setSelectedPatient(@PathVariable("id") int patientId, Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		
		if(user.getData().getClass().equals(PatientData.class)){
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
			String referer = request.getHeader("Referer");
		    return "redirect:"+ referer;
		}else if(user.getData().getClass().equals(DoctorData.class)){
			for(PatientData patient : ((DoctorData)user.getData()).getPatients()){
				if(patient.getId() == patientId){
					userSession.setSelectedPatient(patient);
					break;
				}
			}
			
		}
		return "redirect:/dashboard";
							
		
	}
	
	@Transactional
	@RequestMapping(value = "/dashboard/patient/")
	public String selectPatient(Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession, @RequestParam(value = "patient", required = false) String selectedPatientIdParam) {
		if(!userSession.getData().getClass().equals(DoctorData.class)){
			return "redirect:/dashboard";
		}
		User user = em.merge(userSession);
		String userType = "user";
		
		DoctorData doctor = (DoctorData)em.merge(userSession.getData());
		
		
		if(selectedPatientIdParam != null){
			return "redirect:/dashboard/patient/"+selectedPatientIdParam;
			//int selectedPatientId = Integer.parseInt(selectedPatientIdParam);
			/*for(PatientData patient : doctor.getPatients()){
				if(patient.getId() == selectedPatientId){
					userSession.setSelectedPatient(patient);
					break;
				}
			}*/
			//return "redirect:/dashboard";
		}
		
		int selectedPateintId = -1;
		if(userSession.getSelectedPatient() != null){
			selectedPateintId = userSession.getSelectedPatient().getId();
		}
		
		
		
		model.addAttribute("patients", doctor.getPatients());
		
		model.addAttribute("selectedPatient", selectedPateintId);
		model.addAttribute("page", "home");
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "NADZORNA PLOŠČA MEDIKO");
		model.addAttribute("user", user);
		model.addAttribute("usertype", userType);
		
		return "selectPatient";
			
	}
	
	
	@Transactional
	@RequestMapping(value = "/dashboard")
	public String home(Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession) {
		
		if(UserType.ADMIN.equals(userSession.getUserType())) {
			return "redirect:/admin/createMedicalWorker";
		}
		
		if(userSession.getData() == null){
			return "redirect:/createProfile";
		}
		
		User user = em.merge(userSession);
		
		if(userSession.getData().getClass().equals(PatientData.class)){
			if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
				userSession.setSelectedPatient((PatientData)userSession.getData());
			}
			
			String userType = "user";
			
			PatientData pdata = em.merge(userSession.getSelectedPatient());		
			String fname = pdata.getFirst_name();
			String lname = pdata.getLast_name();
			
			//user == patient
			DoctorData personal_doctor = pdata.getDoctor();
			DoctorData personalDentist = pdata.getDentist();
			List <Appointment> appointments = pdata.getAppointments();
			List <Checkup> checkups = pdata.getCheckups();
			
			List<Disease> diseases = pdata.getDiseases();
			List<Medicine> medicines = pdata.getMedicines();
			List<Diet> diets = pdata.getDiets();
			List<Result_Checkup> results = pdata.getResults();
			PatientData caretaker = (PatientData) pdata.getCaretaker();
			
			model.addAttribute("fname", fname);
			model.addAttribute("lname", lname);
			model.addAttribute("doctor", personal_doctor);
			model.addAttribute("dentist", personalDentist);
			
			model.addAttribute("checkups", checkups);		
			model.addAttribute("diseases", diseases); 
			model.addAttribute("medicines", medicines); 
			model.addAttribute("diets", diets); 
			model.addAttribute("results", results); 
			
			model.addAttribute("appointments", appointments); 
			
			Date now = new Date(Calendar.getInstance().getTime().getTime());
			List<Appointment> upcoming = new ArrayList<Appointment>();
			for (Appointment a : pdata.getAppointments()) {
				Date date = a.getDate();
				if (date.compareTo(now) > 0) {
					upcoming.add(a);
				}
			}
			model.addAttribute("upcoming", upcoming);
			model.addAttribute("caretaker", caretaker); 
					
			//Naloži lazy podatke
			if(user.getData().getClass().equals(PatientData.class)){
				((PatientData)user.getData()).getPatients();
			}
			
			if(UserType.ADMIN.equals(user.getUserType())) {
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
		}else if(userSession.getData().getClass().equals(DoctorData.class)){
			
			String userType = "user";
		
			DoctorData doctor = (DoctorData)em.merge(userSession.getData());
			
			if(userSession.getSelectedPatient() == null){
				return "redirect:/dashboard/patient/";
			}
			
			PatientData pdata = em.merge(userSession.getSelectedPatient());		
			String fname = pdata.getFirst_name();
			String lname = pdata.getLast_name();
			
			//user == patient
			DoctorData personal_doctor = pdata.getDoctor();
			DoctorData personalDentist = pdata.getDentist();
			List <Appointment> appointments = pdata.getAppointments();
			List <Checkup> checkups = pdata.getCheckups();
			
			List<Disease> diseases = pdata.getDiseases();
			List<Medicine> medicines = pdata.getMedicines();
			List<Diet> diets = pdata.getDiets();
			List<Result_Checkup> results = pdata.getResults();
			PatientData caretaker = (PatientData) pdata.getCaretaker();
			
			
			
			model.addAttribute("fname", fname);
			model.addAttribute("lname", lname);
			model.addAttribute("doctor", personal_doctor);
			model.addAttribute("dentist",personalDentist);
			
			model.addAttribute("checkups", checkups);		
			model.addAttribute("diseases", diseases); 
			model.addAttribute("medicines", medicines); 
			model.addAttribute("diets", diets); 
			model.addAttribute("results", results); 
			
			model.addAttribute("appointments", appointments); 
			model.addAttribute("caretaker", caretaker); 
			
			model.addAttribute("selectedPatient", userSession.getSelectedPatient());
			
			model.addAttribute("isDoctor", true);
			model.addAttribute("page", "home");
			model.addAttribute("path", "/mediko_dev/");
			model.addAttribute("title", "NADZORNA PLOŠČA MEDIKO");
			model.addAttribute("user", user);
			model.addAttribute("usertype", userType);
			
			return "home";
			
		}
		else{
			return "home";
		}
	}	
}