package si.fri.t15.rest;

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
}
