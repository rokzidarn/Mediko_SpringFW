package si.fri.t15.admin.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.base.models.UserData;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Instructions;
import si.fri.t15.models.Instructions_Diet;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.UserRole;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.NurseData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.CreateMedicalWorkerForm;
import si.fri.t15.validators.CreateMedicalWorkerValidator;
import si.fri.t15.validators.InsAddDietForm;
import si.fri.t15.validators.InsAddDietValidator;
import si.fri.t15.validators.InsAddDiseaseForm;
import si.fri.t15.validators.InsAddDiseaseValidator;
import si.fri.t15.validators.InsAddMedicineForm;
import si.fri.t15.validators.MedAddDiseaseForm;
import si.fri.t15.validators.MedAddDiseaseValidator;
import si.fri.t15.validators.MedDelDiseaseForm;
import si.fri.t15.validators.MedDelDiseaseValidator;

@Controller
public class CreateMedicalWorkerController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CreateMedicalWorkerValidator createMedicalWorkerValidator;
	
	@InitBinder("command")
	protected void initBinderCMW(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(createMedicalWorkerValidator);
	}
	
	@Autowired
	MedAddDiseaseValidator medAddDiseaseValidator;
	
	@InitBinder("commandadm")
	protected void initBinderADM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(medAddDiseaseValidator);
	}
	
	@Autowired
	MedDelDiseaseValidator medDelDiseaseValidator;
	
	@InitBinder("commandddm")
	protected void initBinderDDM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(medDelDiseaseValidator);
	}
	
	@Transactional
	@RequestMapping(value = "/admin/createMedicalWorker",  method=RequestMethod.POST)
	public ModelAndView createMedicalWorkerPOST(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		
		/*
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Dodaj osebje");
		*/
		
		if (result.hasErrors()) {
			return new ModelAndView("createMedicalWorker");
		}
		
		User user = new User();
		user.setUsername(command.getEmail());
		user.setPassword(passwordEncoder.encode(command.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		Set<UserRole> userRoles = new HashSet<>();
		
		Query userRoleQuery = em.createNamedQuery("UserRole.findByRole");
		UserData uData;
		if (command.getType() == "Nurse") {
			uData = new NurseData();
		} else {
			DoctorData dData = new DoctorData();
			dData.setMaxPatients(command.getMaxPatients());
			dData.setType(command.getTitle());
			uData = dData;
		}
		uData.setFirst_name(command.getFirst_name());
		uData.setLast_name(command.getLast_name());
		uData.setPhoneNumber(command.getPhoneNumber());
		user.setData(uData);
		
		userRoleQuery.setParameter("role", "ROLE_" + command.getType().toUpperCase());

		userRoles.add((UserRole) userRoleQuery.getSingleResult());
		user.setUserRoles(userRoles);
		
		em.persist(uData);
		em.persist(user);
		
		return new ModelAndView("redirect:/admin/createMedicalWorker?successfulRegistration=true");
	}
	
	@RequestMapping(value = "/admin/createMedicalWorker",  method=RequestMethod.GET)
	public ModelAndView createMedicalWorkerGET(Model model, HttpServletRequest request, @RequestParam(required = false) boolean successfulRegistration) {
		
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Dodaj osebje");
		if(successfulRegistration) {
			model.addAttribute("registrationSuccess", "admin.user_registration_successful");
		}
		
		return new ModelAndView("createMedicalWorker");
	}
	
	@RequestMapping(value = "/admin/medicines",  method=RequestMethod.GET)
	public ModelAndView updateMedicinesGET(Model model, HttpServletRequest request) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findAll", Disease.class);
		List<Disease> allDiseases = (List<Disease>) qu.getResultList(); //DDL izbira
		
		//AJAX DA KO IZBERE DISEASE SE IZPISEJO MEDICINES TEGA DISEASE-A
		TypedQuery<Disease> qux = em.createNamedQuery("Disease.findDisease", Disease.class);
		String gripa = " virus ni ";
		Disease d = qux.setParameter(1, gripa).getSingleResult();
		List <Medicine> diseaseMedicines = d.getMedicines();
		model.addAttribute("diseaseMedicines", diseaseMedicines);
		//KONEC
		
		TypedQuery<Medicine> qu2 = em.createNamedQuery("Medicine.findAll", Medicine.class);
		List<Medicine> allMedicines = (List<Medicine>) qu2.getResultList(); //DDL izbira za dodajanje novega
				
		model.addAttribute("allDiseases", allDiseases);
		model.addAttribute("allMedicines", allMedicines);
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Posodobitev bolezni");
		
		return new ModelAndView("medicines");
	}
	
	@Transactional
	@RequestMapping(value = "/admin/medicines/addd",  method=RequestMethod.POST)
	public String addMedicinesPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandadm") @Valid MedAddDiseaseForm commandadm) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, commandadm.getDisease()).getSingleResult();
		
		TypedQuery<Medicine> qu2 = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu2.setParameter(1, commandadm.getMedicine()).getSingleResult(); 
		
		m.getDiseases().add(d);		
		em.merge(m);
		
		return "redirect:/admin/medicines";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/medicines/deld",  method=RequestMethod.POST)
	public String deleteMedicinesPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandddm") @Valid MedDelDiseaseForm commandddm) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, commandddm.getDisease()).getSingleResult();
		
		TypedQuery<Medicine> qu2 = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu2.setParameter(1, commandddm.getMedicine()).getSingleResult(); 
		
		m.getDiseases().remove(d);
		em.merge(m);
		
		return "redirect:/admin/medicines";
	}
}


