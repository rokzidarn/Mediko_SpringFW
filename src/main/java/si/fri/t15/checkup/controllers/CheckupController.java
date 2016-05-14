package si.fri.t15.checkup.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
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
		model.addAttribute("page", "checkup");
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "PREGLED");
		model.addAttribute("user", user);
		return "checkup";
	}
	
	@Transactional
	@RequestMapping(value = "/appointment/{id}", method=RequestMethod.POST)
	public ModelAndView appointmentCheckup(@PathVariable("id") int id,Model model, HttpServletRequest request) {
		TypedQuery<Appointment> qu = em.createNamedQuery("Appointment.findAppointment", Appointment.class);
		qu.setParameter(1,id);
		Appointment curr = qu.setParameter(1, id).getSingleResult();
		
		Checkup recent = new Checkup();
		recent.setAppointment(curr);
		recent.setDoctor(curr.getDoctor());
		recent.setPatient(curr.getPatient());
		
		em.persist(recent);
		
		TypedQuery<Checkup> qu2 = em.createNamedQuery("Checkup.findCheckupByAppointment", Checkup.class);
		qu2.setParameter(1,id);
		Checkup checkup = qu2.setParameter(1, id).getSingleResult();
		int idc = checkup.getCheckupId();
		
		return new ModelAndView("redirect:/checkup/{idc}/insert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/insert", method=RequestMethod.GET)
	public ModelAndView checkupInsertGet(@PathVariable("id") int id,Model model, HttpServletRequest request) {
		
		//trenutni podatki o pregledu, kar je vpisano
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		qu.setParameter(1,id);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		DoctorData d = curr.getDoctor();
		PatientData p = curr.getPatient();
		
		model.addAttribute("idc", id); 
		model.addAttribute("ddata", d); 
		model.addAttribute("pdata", p); 
		model.addAttribute("reason", curr.getReason()); 
		
		model.addAttribute("diseases", curr.getDiseases()); 
		model.addAttribute("diets", curr.getDiets()); 
		model.addAttribute("medicines", curr.getMedicines());
		
		//vse možne bolezni, zdravila, diete iz baze, iz česar bo izbiral zdravnik DDL
		TypedQuery<Disease> qud = em.createNamedQuery("Disease.findAll", Disease.class);
		List<Disease> idiseases = qud.getResultList();		
		model.addAttribute("idiseases", idiseases);
		
		TypedQuery<Diet> qudi = em.createNamedQuery("Diet.findAll", Diet.class);
		List<Diet> idiets = qudi.getResultList();		
		model.addAttribute("idiets", idiets);
		
		TypedQuery<Medicine> qum = em.createNamedQuery("Medicine.findAll", Medicine.class);
		List<Medicine> imedicines = qum.getResultList();		
		model.addAttribute("imedicines", imedicines);
		
		return new ModelAndView("checkupInsert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/disease/{idd}", method=RequestMethod.POST) //<a> gumb redirecta sem, kjer se vstavlja
	public ModelAndView insertDisease(@PathVariable("id") int id, @PathVariable("idd") int idd,Model model, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		qu.setParameter(1,id);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		TypedQuery<Disease> qud = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease idisease = qud.getSingleResult();		
		List<Disease> diseases = new ArrayList<Disease>();
		diseases.add(idisease);
		
		curr.setDiseases(diseases); //dodajanje bolezni na checkup
		
		return new ModelAndView("redirect:/checkup/{idc}/insert");
	}
}
