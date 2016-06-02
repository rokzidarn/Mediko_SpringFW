package si.fri.t15.checkup.controllers;

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

import si.fri.t15.models.Appointment;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;

@Controller
public class OrderCheckupController {

	@Autowired
	EntityManager em;
	
	@Transactional
	@RequestMapping(value = "/checkup/order")
	public String orderCheckup(Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession) {		
		
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
		model.addAttribute("title", "NAROČI NA PREGLED");
		model.addAttribute("user", user);
		
		
		//Get all doctors
		List<DoctorData> doctors = (List<DoctorData>)em.createNamedQuery("DoctorData.GetAllDoctors").getResultList();
		model.addAttribute("doctors",doctors);
		model.addAttribute("selectedDoctor", (userSession.getSelectedPatient() != null)? userSession.getSelectedPatient().getDoctor() : null);
		
		//PLACEHOLDER; ko bomo imeli generacijo urnika se mora to popravit
		List<Appointment> appointments = new ArrayList<Appointment>();
		
		Date todayDate = new Date();
		Calendar calendar = Calendar.getInstance();
        
		//TODO - fix when schedule available
		for(int i = 0; i < 7; i++){
			Appointment appointment = new Appointment();
			appointment.setIdAppointment(i);
			appointment.setDate(new java.sql.Date(calendar.getTime().getTime()));
			appointments.add(appointment);
			calendar.add(Calendar.DATE, 1);
			System.out.println(calendar.getTime());
		}
		
		model.addAttribute("appointments",appointments);
		
		
		return "orderCheckup";

	}
}
