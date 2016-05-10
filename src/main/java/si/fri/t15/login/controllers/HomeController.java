package si.fri.t15.login.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class HomeController extends ControllerBase{
	@Autowired
	private EntityManager em;
	
	@RequestMapping(value = "/home")
	public String home(Model model, HttpServletRequest request,  @AuthenticationPrincipal User activeUser) {
		
		//(User) userinfo.findByUsername(username);
		User user =  em.merge(activeUser);
		
		PatientData pdata = (PatientData) user.getData();
		String fname = pdata.getFirst_name();
		String lname = pdata.getLast_name();
		
		//user == patient
		DoctorData personal_doctor = pdata.getDoctor();
		List <Appointment> appointments = pdata.getAppointments();
		List <Checkup> checkups = pdata.getCheckups(); //DDLIST izbira
		//redirect na stran o podatkih posameznega pregleda, bolezni, zdravila...
		
		List<Disease> diseases = pdata.getDiseases();
		List<Medicine> medicines = pdata.getMedicines();
		List<Diet> diets = pdata.getDiets();
		List<Result_Checkup> results = pdata.getResults();
				
		//če je caretaker si bo izbral varovanca, DDLIST
		List<PatientData> caretaker = (List<PatientData>) pdata.getCaretaker();
		
		model.addAttribute("fname", fname);
		model.addAttribute("lname", lname);
		model.addAttribute("doctor", personal_doctor);
		
		model.addAttribute("checkups", checkups);		
		model.addAttribute("diseases", diseases); 
		model.addAttribute("medicines", medicines); 
		model.addAttribute("diets", diets); 
		model.addAttribute("results", results); 
		
		model.addAttribute("appointments", appointments); 
		model.addAttribute("caretaker", caretaker); 
				
		//Side menu variables
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "Nadzorna plošča");
		return "home";
	}	
}