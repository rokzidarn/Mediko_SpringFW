package si.fri.t15.admin.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.MedAddDiseaseForm;
import si.fri.t15.validators.MedAddDiseaseValidator;
import si.fri.t15.validators.MedDelDiseaseForm;
import si.fri.t15.validators.MedDelDiseaseValidator;

@Controller
public class MedicineController extends ControllerBase{
	@Autowired
	EntityManager em;
	
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
	
	@RequestMapping(value = "/admin/medicines",  method=RequestMethod.GET)
	public ModelAndView updateMedicinesGET(Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession) {
		
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
				
		model.addAttribute("user", userSession);
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
