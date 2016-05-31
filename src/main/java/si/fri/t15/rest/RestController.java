package si.fri.t15.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import si.fri.t15.dao.UserRepository;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Instructions;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;

@Controller
public class RestController {

	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/api/patient/{id}/checkup", method=RequestMethod.GET)
	@ResponseBody
	public List<Checkup> getPatientCheckups(@PathVariable("id") int patientId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		PatientData data = getPatient(patientId, user);
		if(data != null)
			return data.getCheckups();
		else
			return new ArrayList<Checkup>();
	}
	
	@Transactional
	@RequestMapping(value = "/api/patient/{id}/disease/{alergy}", method=RequestMethod.GET)
	@ResponseBody
	public List<Disease> getPatientDiseases(@PathVariable("id") int patientId,@PathVariable("alergy") int isAlergy,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		PatientData data = getPatient(patientId, user);
		if(data != null){
			return data.getDiseases(isAlergy);
		}
		else
			return new ArrayList<Disease>();
	}
	
	@Transactional
	@RequestMapping(value = "/api/patient/{id}/diet", method=RequestMethod.GET)
	@ResponseBody
	public List<Diet> getPatientDiets(@PathVariable("id") int patientId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		PatientData data = getPatient(patientId, user);
		if(data != null){
			return data.getDiets();
		}
		else
			return new ArrayList<Diet>();
	}
	
	@Transactional
	@RequestMapping(value = "/api/patient/{id}/medicine", method=RequestMethod.GET)
	@ResponseBody
	public List<Medicine> getPatientMedicines(@PathVariable("id") int patientId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		PatientData data = getPatient(patientId, user);
		if(data != null){
			return data.getMedicines();
		}
		else
			return new ArrayList<Medicine>();
	}
	
	@Transactional
	@RequestMapping(value = "/api/patient/{id}/appointment", method=RequestMethod.GET)
	@ResponseBody
	public List<Appointment> getPatientAppointments(@PathVariable("id") int patientId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		PatientData data = getPatient(patientId, user);
		if(data != null){
			return data.getAppointments();
		}
		else
			return new ArrayList<Appointment>();
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/api/instructions/disease/{id}",  method=RequestMethod.GET)
	public List<Instructions> showInsDisease(Model model, HttpServletRequest request,@PathVariable("id") String id) {
		TypedQuery<Disease> qux = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qux.setParameter(1, id).getSingleResult();
		
		TypedQuery<Instructions> qu4 = em.createNamedQuery("Instructions.findAll", Instructions.class);
		List<Instructions> allInstructions = (List<Instructions>) qu4.getResultList();
		
		List<Instructions> diseaseInstructions = new ArrayList<>();
		
		for(Instructions icurr : allInstructions){
			if(icurr.getDisease() == d)
				diseaseInstructions.add(icurr);
		}
	
		return diseaseInstructions;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/api/instructions/diet/{id}",  method=RequestMethod.GET)
	public List<Instructions> showInsDiet(Model model, HttpServletRequest request,@PathVariable("id") int id) {
		TypedQuery<Diet> qux = em.createNamedQuery("Diet.findDiet", Diet.class);
		Diet di = qux.setParameter(1, id).getSingleResult();
		
		TypedQuery<Instructions> qu4 = em.createNamedQuery("Instructions.findAll", Instructions.class);
		List<Instructions> allInstructions = (List<Instructions>) qu4.getResultList();
		
		List<Instructions> dietInstructions = new ArrayList<>();
		
		for(Instructions icurr : allInstructions){
			if(icurr.getDiet() == di)
				dietInstructions.add(icurr);
		}
	
		return dietInstructions;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/api/instructions/medicine/{id}",  method=RequestMethod.GET)
	public List<Instructions> showInsMedicine(Model model, HttpServletRequest request,@PathVariable("id") int id) {
		TypedQuery<Medicine> qux = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qux.setParameter(1, id).getSingleResult();
		
		TypedQuery<Instructions> qu4 = em.createNamedQuery("Instructions.findAll", Instructions.class);
		List<Instructions> allInstructions = (List<Instructions>) qu4.getResultList();
		
		List<Instructions> medicineInstructions = new ArrayList<>();
		
		for(Instructions icurr : allInstructions){
			if(icurr.getMedicine() == m)
				medicineInstructions.add(icurr);
		}
	
		return medicineInstructions;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/api/medicines/disease/{id}",  method=RequestMethod.GET)
	public List<Medicine> showMedicines(Model model, HttpServletRequest request,@PathVariable("id") String id) {
		TypedQuery<Disease> qux = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qux.setParameter(1, id).getSingleResult();
		
		List<Medicine> m = d.getMedicines();
	
		return m;
	}
	
	
	//helpers
	public PatientData getPatient(int patientId, User user){
		PatientData data = null;
		if(user.getData() != null){
			if(user.getData().getId() == patientId){
				data = (PatientData)user.getData();
			}
			else{
				for(PatientData patient : ((PatientData)user.getData()).getPatients()){
					if(patient.getId() == patientId){
						data = patient;
						break;
					}
				}
			}
		}
		return data;
	}
}
