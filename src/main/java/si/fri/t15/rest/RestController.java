package si.fri.t15.rest;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.Result_Checkup;
import si.fri.t15.models.WorkDay;
import si.fri.t15.models.WorkWeek;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.models.user.User.UserType;

@Controller
public class RestController {

	@Autowired
	EntityManager em;
	
	//patient
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
	@RequestMapping(value = "/api/patient/{id}/result", method=RequestMethod.GET)
	@ResponseBody
	public List<Result_Checkup> getPatientResults(@PathVariable("id") int patientId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		PatientData data = getPatient(patientId, user);
		if(data != null){
			return data.getResults();
		}
		else
			return new ArrayList<Result_Checkup>();
	}
	
	//Doctor
	@Transactional
	@RequestMapping(value = "/api/doctor/{id}/appointment/available", method=RequestMethod.GET)
	@ResponseBody
	public List<Appointment> getDoctorsAvailableAppointments(@PathVariable("id") int doctorId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
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
		
		
		
		return appointments;
	}

	//admin
	@Transactional
	@RequestMapping(value = "/api/user/notcompleted")
	@ResponseBody
	public List<User> getUsersNotCompleted(
			@RequestParam(required=false, defaultValue="rd") String filterTypeInput,
			@RequestParam(required=false, defaultValue="") String searchInput,
			@RequestParam(required=false, defaultValue="0") int hitsNumberInput,
			@RequestParam(required=false, defaultValue="asc") String orderTypeInput,
			@RequestParam(required=false, defaultValue="true") boolean showUser,
			@RequestParam(required=false, defaultValue="true") boolean showDoctor,
			@RequestParam(required=false, defaultValue="true") boolean showNurse,
			
			HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		/*Query query = em.createNamedQuery("User.findAllWithoutUserData");
		query.setParameter("search", "%"+searchInput+"%");*/
		String orderBy = "registrationDate";
		switch(filterTypeInput){
			case "rd":
				orderBy = "u.registrationDate";
				break;
			case "em":
				orderBy = "u.username";
				break;
		}
		
		Query query = em.createQuery("SELECT u FROM User u WHERE u.data = null AND u.username LIKE :search "+"ORDER BY "+(orderBy+" "+(orderTypeInput.equals("asc")?"ASC ":"DESC ")));
		query.setParameter("search", "%"+searchInput+"%");
		//+" "+(orderTypeInput.equals("asc")?"ASC":"DESC")
		//query.setParameter("orderBy", orderBy);
		
		if(hitsNumberInput>0){
			query.setMaxResults(hitsNumberInput);
		}
		List<User> users = (List<User>)query.getResultList();
		for(int i = 0; i <users.size(); i++){
			if(users.get(i).getUserType().equals(UserType.ADMIN)){
				users.remove(i);
				i--;
				continue;
			}
			if(!showUser && users.get(i).getUserType().equals(UserType.USER)){
				users.remove(i);
				i--;
				continue;
			}
			if(!showDoctor && users.get(i).getUserType().equals(UserType.DOCTOR)){
				users.remove(i);
				i--;
				continue;
			}
			if(!showNurse && users.get(i).getUserType().equals(UserType.NURSE)){
				users.remove(i);
				i--;
				continue;
			}
		}
		return users;
		
		
	}
	
	//Doctor
	@Transactional
	@RequestMapping(value = "/api/doctor/{id}/workweek", method=RequestMethod.GET)
	@ResponseBody
	public List<WorkWeek> getDoctorsWorkWeeks(@PathVariable("id") int doctorId,HttpServletRequest request,@AuthenticationPrincipal User userSession){
		List<WorkWeek> allWorkWeeks = new ArrayList<WorkWeek>();
		
		User user = em.merge(userSession);
		
		//TODAY
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		
		while(calendar.get(Calendar.DAY_OF_WEEK) > 2){
			calendar.add(Calendar.DATE, -1);
		}
		for(int i = 0; i < 3; i++){
			//Find or create workweek
			List<WorkWeek> workWeeks = (List<WorkWeek>)em.createNamedQuery("WorkWeek.getWorkWeekByStartDateAndDoctor")
										.setParameter("startDate", calendar.getTime())
										.setParameter("doctor", user.getData()).getResultList();
			WorkWeek workWeek = null;
			if(workWeeks.isEmpty()){
				workWeek = new WorkWeek();
				workWeek.setStartDate(new java.sql.Date(calendar.getTime().getTime()));
				workWeek.setDoctor((DoctorData)user.getData());
	
				em.persist(workWeek);
			}else{
				workWeek = workWeeks.get(0);
			}
			
			//DAYS
			if(workWeek.getWorkDays().isEmpty()){
				for(int d = 0; d < 6; d++){
					WorkDay workDay = new WorkDay();
					workDay.setWorkWeek(workWeek);
					workWeek.getWorkDays().add(workDay);
					em.persist(workDay);
				}
			}
			
			allWorkWeeks.add(workWeek);
			calendar.add(Calendar.DATE, 7);
		}
		/*//TODO - fix when schedule available
		for(int i = 0; i < 7; i++){
			Appointment appointment = new Appointment();
			appointment.setIdAppointment(i);
			appointment.setDate(new java.sql.Date(calendar.getTime().getTime()));
			appointments.add(appointment);
			calendar.add(Calendar.DATE, 1);
			System.out.println(calendar.getTime());
		}*/
		
		
		
		return allWorkWeeks;
	}
	
	@Transactional
	@RequestMapping(value = "/api/doctor/updateworkday")
	@ResponseBody
	public List<Map<String, Object>> updateDoctorsWorkDay(
			@RequestParam(required=true) int workDayId,
			@RequestParam(required=true) String workDayStart,
			@RequestParam(required=true) String workDayEnd,
			@RequestParam(required=true) int workDayInterval,
			@RequestParam(required=false, defaultValue="") String workDayNote,
			HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		User user = em.merge(userSession);
		
		Time workDayStartTime = Time.valueOf(workDayStart+":00");
		Time workDayEndTime = Time.valueOf(workDayEnd+":00");
		
		List<WorkDay> queryResult = em.createNamedQuery("WorkDay.getWorkdayById").setParameter("id", workDayId).getResultList();
		
		//Check if workday exist
		if(queryResult.isEmpty()) return new ArrayList<Map<String, Object>>();
		
		WorkDay workDay = queryResult.get(0);
		
		//check if current doctor is owner of this workday
		if(!workDay.getWorkWeek().getDoctor().equals(user.getData())) return new ArrayList<Map<String, Object>>();
		
		workDay.setNote(workDayNote);
		
		if(workDay.getStart() != null && workDay.getEnd() != null &&workDay.getStart().equals(workDayStartTime) && workDay.getEnd().equals(workDayEndTime) && workDay.getMinuteInterval() == workDayInterval) return MapAppointmentWithPatient(workDay.getAppointments());
		
		//remove all old appointments
		for(Appointment a : workDay.getAppointments()){
			em.remove(a);
		}
		
		workDay.setStart(workDayStartTime);
		workDay.setEnd(workDayEndTime);
		workDay.setMinuteInterval(workDayInterval);
		
		
		Calendar workDayDate = Calendar.getInstance();
		workDayDate.setTime(workDay.getWorkWeek().getStartDate());
		workDayDate.add(Calendar.DATE, workDay.getWorkWeek().getWorkDays().indexOf(workDay));
		
		Calendar timeCalendar = Calendar.getInstance();
		timeCalendar.setTime(new Date(workDayStartTime.getTime()));
		timeCalendar.set(workDayDate.get(Calendar.YEAR), workDayDate.get(Calendar.MONTH), workDayDate.get(Calendar.DATE));
		
		
		Calendar endTimeCalendar = Calendar.getInstance();
		endTimeCalendar.setTime(new Date(workDayEndTime.getTime()));
		endTimeCalendar.add(Calendar.MINUTE, -workDayInterval);
		endTimeCalendar.set(workDayDate.get(Calendar.YEAR), workDayDate.get(Calendar.MONTH), workDayDate.get(Calendar.DATE));
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		
		while(timeCalendar.compareTo(endTimeCalendar) <= 0){
			Appointment appointment = new Appointment();
			
			Timestamp t = new Timestamp(timeCalendar.getTime().getTime());
			appointment.setDateTime(t);
			appointment.setDoctor((DoctorData)user.getData());
			appointment.setWorkDay(workDay);
			workDay.getAppointments().add(appointment);
			
			em.persist(appointment);

			appointments.add(appointment);
			
			timeCalendar.add(Calendar.MINUTE, workDayInterval);
		}
		
		
		
		em.persist(workDay);
		
		
		
		return MapAppointmentWithPatient(appointments);
	}
	
	@Transactional
	@RequestMapping(value = "/api/doctor/copyworkweek")
	@ResponseBody
	public List<WorkWeek> copyWorkWeeks(
			@RequestParam(required=true) int currentWeekId,
			@RequestParam(required=true) int destinationWeekId,
			HttpServletRequest request,@AuthenticationPrincipal User userSession)
	{
		User user = em.merge(userSession);
		
		List<WorkWeek> currentWorkWeekList = em.createNamedQuery("WorkWeek.getWorkWeekById").setParameter("id", currentWeekId).getResultList();
		if(currentWorkWeekList.isEmpty()) return new ArrayList<WorkWeek>();
		WorkWeek currentWorkWeek = currentWorkWeekList.get(0);
		
		List<WorkWeek> destinationWorkWeekList = em.createNamedQuery("WorkWeek.getWorkWeekById").setParameter("id", destinationWeekId).getResultList();
		if(destinationWorkWeekList.isEmpty()) return new ArrayList<WorkWeek>();
		WorkWeek destinationWorkWeek = destinationWorkWeekList.get(0);
		
		if(!currentWorkWeek.getDoctor().equals(user.getData())|| !destinationWorkWeek.getDoctor().equals(user.getData()))return new ArrayList<WorkWeek>();
		
		for(int i= 0; i < currentWorkWeek.getWorkDays().size(); i++){
			WorkDay currentWorkDay = currentWorkWeek.getWorkDays().get(i);
			
			WorkDay destinationWorkDay = destinationWorkWeek.getWorkDays().get(i);
			destinationWorkDay.setStart(currentWorkDay.getStart());
			destinationWorkDay.setEnd(currentWorkDay.getEnd());
			destinationWorkDay.setMinuteInterval(currentWorkDay.getMinuteInterval());
			destinationWorkDay.setNote(currentWorkDay.getNote());
			
			
			if(destinationWorkDay.getStart() == null || destinationWorkDay.getEnd() == null || destinationWorkDay.getMinuteInterval() == 0) continue;
			//Calendaring
			Calendar workDayDate = Calendar.getInstance();
			workDayDate.setTime(destinationWorkDay.getWorkWeek().getStartDate());
			workDayDate.add(Calendar.DATE, destinationWorkDay.getWorkWeek().getWorkDays().indexOf(destinationWorkDay));
			
			Calendar timeCalendar = Calendar.getInstance();
			timeCalendar.setTime(new Date(destinationWorkDay.getStart().getTime()));
			timeCalendar.set(workDayDate.get(Calendar.YEAR), workDayDate.get(Calendar.MONTH), workDayDate.get(Calendar.DATE));
			
			
			Calendar endTimeCalendar = Calendar.getInstance();
			endTimeCalendar.setTime(new Date(destinationWorkDay.getEnd().getTime()));
			endTimeCalendar.add(Calendar.MINUTE, -destinationWorkDay.getMinuteInterval());
			endTimeCalendar.set(workDayDate.get(Calendar.YEAR), workDayDate.get(Calendar.MONTH), workDayDate.get(Calendar.DATE));
			
			//GENERATE appointments
			while(timeCalendar.compareTo(endTimeCalendar) <= 0){
				Appointment appointment = new Appointment();
				
				Timestamp t = new Timestamp(timeCalendar.getTime().getTime());
				appointment.setDateTime(t);
				appointment.setDoctor((DoctorData)user.getData());
				appointment.setWorkDay(destinationWorkDay);
				destinationWorkDay.getAppointments().add(appointment);
				
				em.persist(appointment);

								
				timeCalendar.add(Calendar.MINUTE, destinationWorkDay.getMinuteInterval());
			}
			em.persist(destinationWorkDay);
		}
		List<WorkWeek> out = new ArrayList<WorkWeek>();
		out.add(destinationWorkWeek);
		return out;
	}
	@Transactional
	@RequestMapping(value = "/api/doctor/setfreetime")
	@ResponseBody
	public Appointment setDoctorFreeTime(
			@RequestParam(required=true) int appointmentId,
			HttpServletRequest request,@AuthenticationPrincipal User userSession)
	{
		User user = em.merge(userSession);
		//GET appointment
		Appointment appointment = (Appointment) em.createNamedQuery("Appointment.findAppointment").setParameter(1, appointmentId).getSingleResult();
		
		if(!appointment.getDoctor().equals(user.getData())) return null;
		
		appointment.setDoctorFreeTime(!appointment.isDoctorFreeTime());
		
		em.persist(appointment);
		
		return appointment;
	}
	
	
	@Transactional
	@RequestMapping(value = "/api/user/new")
	@ResponseBody
	public List<User> getUsersNew(
			@RequestParam(required=false, defaultValue="rd") String filterTypeInput,
			@RequestParam(required=false, defaultValue="") String searchInput,
			@RequestParam(required=false, defaultValue="0") int hitsNumberInput,
			@RequestParam(required=false, defaultValue="asc") String orderTypeInput,
			@RequestParam(required=false, defaultValue="true") boolean showUser,
			@RequestParam(required=false, defaultValue="true") boolean showDoctor,
			@RequestParam(required=false, defaultValue="true") boolean showNurse,
			@RequestParam(required=false, defaultValue="2016-01-01") String fromInput,
			@RequestParam(required=false, defaultValue="2017-01-01") String toInput,
			
			HttpServletRequest request,@AuthenticationPrincipal User userSession){
		
		String orderBy = "registrationDate";
		switch(filterTypeInput){
			case "rd":
				orderBy = "u.registrationDate";
				break;
			case "em":
				orderBy = "u.username";
				break;
		}
		
		
		Query query = em.createQuery("SELECT u FROM User u WHERE u.registrationDate >= :fromInput AND u.registrationDate <= :toInput AND u.username LIKE :search "+"ORDER BY "+(orderBy+" "+(orderTypeInput.equals("asc")?"ASC ":"DESC ")));
		
		query.setParameter("fromInput",java.sql.Date.valueOf(fromInput));
		query.setParameter("toInput", java.sql.Date.valueOf(toInput));
		query.setParameter("search", "%"+searchInput+"%");
		
		
		if(hitsNumberInput>0){
			query.setMaxResults(hitsNumberInput);
		}
		
		List<User> users = (List<User>)query.getResultList();
		for(int i = 0; i <users.size(); i++){
			if(users.get(i).getUserType().equals(UserType.ADMIN)){
				users.remove(i);
				i--;
				continue;
			}
			if(!showUser && users.get(i).getUserType().equals(UserType.USER)){
				users.remove(i);
				i--;
				continue;
			}
			if(!showDoctor && users.get(i).getUserType().equals(UserType.DOCTOR)){
				users.remove(i);
				i--;
				continue;
			}
			if(!showNurse && users.get(i).getUserType().equals(UserType.NURSE)){
				users.remove(i);
				i--;
				continue;
			}
		}
		return users;
	}
	//helpers
	public PatientData getPatient(int patientId, User user){
		PatientData data = null;
		if(user.getData() != null){
			if(user.getData().getClass().equals(PatientData.class)){
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
			}else if(user.getData().getClass().equals(DoctorData.class)){
				for(PatientData patient : ((DoctorData)user.getData()).getPatients()){
					if(patient.getId() == patientId){
						data = patient;
						break;
					}
				}
			}
		}
		return data;
	}
	
	public List<Map<String, Object>> MapAppointmentWithPatient(List<Appointment> appointments){
		List<Map<String, Object>> appointmentsMap = new ArrayList<Map<String, Object>>();
		for(Appointment appointment : appointments){
			Map<String, Object> appointmentMap = new HashMap<String, Object>();
			appointmentMap.put("dateTime", appointment.getDateTime());
			appointmentMap.put("id", appointment.getIdAppointment());
			appointmentMap.put("patient", appointment.getPatient());
			appointmentMap.put("isTaken", appointment.isTaken());
			appointmentMap.put("workDayId", appointment.getWorkDay().getId());
			appointmentsMap.add(appointmentMap);
		}
		
		return appointmentsMap;
	}
}
