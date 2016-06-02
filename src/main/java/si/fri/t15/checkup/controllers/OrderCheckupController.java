package si.fri.t15.checkup.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;

@Controller
public class OrderCheckupController extends ControllerBase{

	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/checkup/order", method=RequestMethod.POST)
	public String orderCheckupInsert(
			@RequestParam(required=false, defaultValue="-1") int appointment,
			@RequestParam(required=false, defaultValue="-1") int doctor, Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession){
		
		if(appointment == -1) return "redirect:/checkup/order";
		
		List<Appointment> listOfAppointments = em.createNamedQuery("Appointment.findAppointment").setParameter(1,appointment).getResultList();
		
		if(listOfAppointments.isEmpty())return "redirect:/checkup/order";
		
		Appointment a = listOfAppointments.get(0);
		
		if(a.getDoctor()!=null && a.getDoctor().getId() != doctor) return "redirect:/checkup/order";
		
		a.setPatient(userSession.getSelectedPatient());
		
		em.merge(a);
		
		return "redirect:/dashboard";
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/order")
	public String orderCheckup(Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession) {		
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		//Side menu variables
		String userType = "user";
		User user = em.merge(userSession);
		
		if(userSession.getData().getClass().equals(PatientData.class)){
			PatientData p = em.merge(userSession.getSelectedPatient());
			
			//Side menu variables
			model.addAttribute("selectedPatient", userSession.getSelectedPatient());
			
			
			//Get all doctors
			List<DoctorData> doctors = (List<DoctorData>)em.createNamedQuery("DoctorData.GetAllDoctors").getResultList();
			model.addAttribute("doctors",doctors);
			model.addAttribute("selectedDoctor", (userSession.getSelectedPatient() != null)? userSession.getSelectedPatient().getDoctor() : null);
		}else if(userSession.getData().getClass().equals(DoctorData.class)){
		
			DoctorData doctor = (DoctorData)user.getData();

			model.addAttribute("patients", doctor.getPatients());
			model.addAttribute("isDoctor", true);
			
		}
		
		model.addAttribute("usertype", userType);
		
		model.addAttribute("page", "checkup");
		model.addAttribute("subpage", "order");
		//Page variables
		model.addAttribute("title", "NAROČI NA PREGLED");
		model.addAttribute("user", user);
		
		return "orderCheckup";

	}

	@Transactional
	@RequestMapping(value = "/checkup/list")
	public String checkupList(Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession){
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		//Side menu variables
		String userType = "user";
		User user = em.merge(userSession);
		PatientData p = em.merge(userSession.getSelectedPatient());
		
		//Side menu variables
		model.addAttribute("usertype", userType);
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("page", "checkup");
		model.addAttribute("subpage", "order");
		//Page variables
		model.addAttribute("title", "SEZNAM NAROČENIH PREGLEDOV");
		model.addAttribute("user", user);
		
		//Get all doctors
		List<Appointment> upcomingAppointments = new ArrayList<Appointment>();
		
		for(Appointment a : p.getAppointments()){
			if(a.getDateTime().getTime() > Calendar.getInstance().getTimeInMillis()){
				upcomingAppointments.add(a);
			}
		}
		
		model.addAttribute("upcomingAppointments", upcomingAppointments);

		
		
		return "checkupList";
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/list", method=RequestMethod.POST)
	public String checkupListRelease(@RequestParam(required=false, defaultValue="-1") int appointmentId, Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession){
		
		if(appointmentId == -1) return "redirect:/checkup/list";
		
		List<Appointment> listOfAppointments = em.createNamedQuery("Appointment.findAppointment").setParameter(1,appointmentId).getResultList();
		
		if(listOfAppointments.isEmpty())return "redirect:/checkup/list";
		
		Appointment a = listOfAppointments.get(0);
		Calendar today = Calendar.getInstance();
		today.add(Calendar.HOUR, +12);
		
		if(a.getDateTime().getTime() > today.getTimeInMillis()){
			a.setPatient(null);
			em.persist(a);
		}
		
		return "redirect:/checkup/list";
	}
}
