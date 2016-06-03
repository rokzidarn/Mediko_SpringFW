package si.fri.t15.checkup.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
			@RequestParam(required=false, defaultValue="-1") String appointment,
			@RequestParam(required=false, defaultValue="-1") int doctor,
			@RequestParam(required=false, defaultValue="-1") int patient, Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession){
		
		if(appointment == "-1") return "redirect:/checkup/order";
		if(userSession.getData().getClass().equals(PatientData.class)){
			int appointmentId = -1;
			try{
				appointmentId = Integer.parseInt(appointment);
			}catch(Exception ex){
				return "redirect:/checkup/order";
			}
			List<Appointment> listOfAppointments = em.createNamedQuery("Appointment.findAppointment").setParameter(1,appointmentId).getResultList();
			
			if(listOfAppointments.isEmpty())return "redirect:/checkup/order";
			
			Appointment a = listOfAppointments.get(0);
			
			if(a.getDoctor()!=null && a.getDoctor().getId() != doctor) return "redirect:/checkup/order";
			
			a.setPatient(userSession.getSelectedPatient());
			a.setOrderedBy(userSession.getData());
			
			em.merge(a);
			return "redirect:/dashboard";
		}else if(userSession.getData().getClass().equals(DoctorData.class)){
			
			String [] appointments = appointment.split(",");
			try{
				Query getAppointmentQuery = em.createNamedQuery("Appointment.findAppointment");
				for(int i = 0; i < appointments.length; i++){
					int appointmentId = Integer.parseInt(appointments[i]);
					getAppointmentQuery.setParameter(1, appointmentId);
					
					Appointment a = (Appointment)getAppointmentQuery.getSingleResult();
					
					if(a.getDoctor() != null && a.getDoctor().getId() != userSession.getData().getId()) return "redirect:/checkup/order";
					
					PatientData patientData = em.find(PatientData.class, patient);
					
					if(patientData == null ) return "redirect:/checkup/order";
					
					a.setPatient(patientData);
					a.setOrderedBy(userSession.getData());
					
					em.persist(a);
					
				}
				
				return "redirect:/checkup/list";
				
				
				
				
			}catch(Exception ex){return "redirect:/checkup/order";}
		}
		return "redirect:/dashboard";
		
		
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/order/doctor", method=RequestMethod.POST)
	public String orderCheckupInsert(
			@RequestParam(required=false, defaultValue="") String appointment,
			@RequestParam(required=false, defaultValue="-1") int patient, Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession){
		
		/*if(appointment == -1) return "redirect:/checkup/order";
		
		List<Appointment> listOfAppointments = em.createNamedQuery("Appointment.findAppointment").setParameter(1,appointment).getResultList();
		
		if(listOfAppointments.isEmpty())return "redirect:/checkup/order";
		
		Appointment a = listOfAppointments.get(0);
		
		if(a.getDoctor()!=null && a.getDoctor().getId() != doctor) return "redirect:/checkup/order";
		
		a.setPatient(userSession.getSelectedPatient());
		
		em.merge(a);*/
		
		return "redirect:/checkup/order";
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

			model.addAttribute("doctor", doctor);
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
		
		if(userSession.getData().getClass().equals(PatientData.class)){
			
			PatientData p = em.merge(userSession.getSelectedPatient());
			
			//Side menu variables
			
			model.addAttribute("selectedPatient", userSession.getSelectedPatient());
			
			//Get all appointments
			List<Appointment> upcomingAppointments = new ArrayList<Appointment>();
			
			for(Appointment a : p.getAppointments()){
				if(a.getDateTime().getTime() > Calendar.getInstance().getTimeInMillis()){
					upcomingAppointments.add(a);
				}
			}
			
			model.addAttribute("upcomingAppointments", upcomingAppointments);
		}
		else if(userSession.getData().getClass().equals(DoctorData.class)){
			DoctorData doctorData = (DoctorData)user.getData();
			
			List<Appointment> upcomingAppointments = new ArrayList<Appointment>();
			
			for(Appointment a : doctorData.getAppointments()){
				if(a.getDateTime().getTime() > Calendar.getInstance().getTimeInMillis() && a.getPatient() != null){
					upcomingAppointments.add(a);
				}
			}
			model.addAttribute("upcomingAppointments", upcomingAppointments);
			model.addAttribute("isDoctor", true);
		}
		
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "checkup");
		model.addAttribute("subpage", "list");
		//Page variables
		model.addAttribute("title", "SEZNAM NAROČENIH PREGLEDOV");
		model.addAttribute("user", user);

		
		
		return "checkupList";
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/list", method=RequestMethod.POST)
	public String checkupListRelease(@RequestParam(required=false, defaultValue="-1") int appointmentId, Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession){
		
		if(appointmentId == -1) return "redirect:/checkup/list";
		
		List<Appointment> listOfAppointments = em.createNamedQuery("Appointment.findAppointment").setParameter(1,appointmentId).getResultList();
		
		if(listOfAppointments.isEmpty())return "redirect:/checkup/list";
		
		Appointment a = listOfAppointments.get(0);
		if(userSession.getData().getClass().equals(PatientData.class)){
			
			Calendar today = Calendar.getInstance();
			today.add(Calendar.HOUR, +12);
			
			if(a.getDateTime().getTime() > today.getTimeInMillis()){
				a.setPatient(null);
				a.setOrderedBy(null);
				em.persist(a);
			}
		}else if(userSession.getData().getClass().equals(DoctorData.class)){
			a.setPatient(null);
			a.setOrderedBy(null);
			em.persist(a);
		}
		
		return "redirect:/checkup/list";
	}
}
