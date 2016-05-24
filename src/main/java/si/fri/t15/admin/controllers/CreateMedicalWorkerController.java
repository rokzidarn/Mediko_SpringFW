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
import si.fri.t15.validators.InsAddDietForm;
import si.fri.t15.validators.InsAddDiseaseForm;
import si.fri.t15.validators.InsAddMedicineForm;

@Controller
public class CreateMedicalWorkerController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator);
	}
 
	@Resource(name="createMedicalWorkerValidator")
	Validator validator;
	
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
	
	@RequestMapping(value = "/admin/dadd/{did}/m/{mid}",  method=RequestMethod.POST)
	public ModelAndView addMedicinesPOST(Model model, HttpServletRequest request, @PathVariable("did") String did, @PathVariable("mid") int mid) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, did).getSingleResult();
		
		TypedQuery<Medicine> qu2 = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu2.setParameter(1, mid).getSingleResult(); 
		
		List<Medicine> currm = d.getMedicines();
		currm.add(m);		
		em.merge(d);
		
		return new ModelAndView("medicines");
	}
	
	@RequestMapping(value = "/admin/ddel/{did}/m/{mid}",  method=RequestMethod.POST)
	public ModelAndView deleteMedicinesPOST(Model model, HttpServletRequest request, @PathVariable("did") String did, @PathVariable("mid") int mid) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, did).getSingleResult();
		
		TypedQuery<Medicine> qu2 = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu2.setParameter(1, mid).getSingleResult(); 
		
		List<Medicine> currm = d.getMedicines();
		currm.remove(m);	
		em.merge(d);
		
		return new ModelAndView("medicines");
	}
	
	@RequestMapping(value = "/admin/instructions",  method=RequestMethod.GET)
	public ModelAndView updateInstructionsGET(Model model, HttpServletRequest request) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findAll", Disease.class);
		List<Disease> allDiseases = (List<Disease>) qu.getResultList(); 
		
		TypedQuery<Diet> qu2 = em.createNamedQuery("Diet.findAll", Diet.class);
		List<Diet> allDiets = (List<Diet>) qu2.getResultList(); 
		
		TypedQuery<Medicine> qu3 = em.createNamedQuery("Medicine.findAll", Medicine.class);
		List<Medicine> allMedicines = (List<Medicine>) qu3.getResultList();
		
		//AJAX DA KO IZBERE DISEASE SE IZPISEJO INSTRUCTIONS TEGA DISEASE-A		
		TypedQuery<Disease> qux = em.createNamedQuery("Disease.findDisease", Disease.class);
		String gripa = " virus ni ";
		Disease d = qux.setParameter(1, gripa).getSingleResult();
		//List <Instructions> insDiseases = d.getInstructions_Diseases(); //TODO
		model.addAttribute("insDiseases", null);
		//KONEC
				
		//AJAX DA KO IZBERE DIET SE IZPISEJO INSTRUCTIONS TEGA DIET-A
		TypedQuery<Diet> qux2 = em.createNamedQuery("Diet.findDiet", Diet.class);
		int celiakija = 1;
		Diet di = qux2.setParameter(1, celiakija).getSingleResult();
		List <Instructions_Diet> insDiets = di.getInstructions_Diets();
		model.addAttribute("insDiets", insDiets);
		//KONEC
		
		//AJAX DA KO IZBERE MEDICINE SE IZPISEJO INSTRUCTIONS TEGA MEDICINE-A
		TypedQuery<Medicine> qux3 = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		int enalapril = 1;
		Medicine m = qux3.setParameter(1, enalapril).getSingleResult();
		List <Instructions> insMedicines = m.getInstructions();
		model.addAttribute("insMedicines", insMedicines);
		//KONEC
				
		model.addAttribute("allDiseases", allDiseases);
		model.addAttribute("allDiets", allDiets);
		model.addAttribute("allMedicines", allMedicines);
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Posodobitev navodil");
		
		return new ModelAndView("instructions");
	}
	
	@RequestMapping(value = "/admin/dad/{did}",  method=RequestMethod.POST)
	public ModelAndView addDiseaseInstructionsPOST(Model model, HttpServletRequest request, @PathVariable("did") String did,
			@ModelAttribute("commandiad") @Valid InsAddDiseaseForm commandiad) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, did).getSingleResult();
		/*
		Instructions_Disease i = new Instructions_Disease();
		i.setName("Navodilo za "+d.getName());
		String text = "text"; //TODO -> validacija
		i.setText(text);
		i.setDuration(0);
		
		List<Instructions_Disease>	curr = di.getInstructions_Diseases();
		curr.add(i);
		em.merge(d);
		*/
		return new ModelAndView("instructions");
	}
	
	@RequestMapping(value = "/admin/diadd/{ddid}",  method=RequestMethod.POST)
	public ModelAndView addDietInstructionsPOST(Model model, HttpServletRequest request, @PathVariable("ddid") String ddid,
			@ModelAttribute("commandiadi") @Valid InsAddDietForm commandiadi) {
		
		TypedQuery<Diet> qu = em.createNamedQuery("Diet.findDiet", Diet.class);
		Diet di = qu.setParameter(1, ddid).getSingleResult();
		
		Instructions_Diet i = new Instructions_Diet();
		i.setName("Navodilo za "+di.getName());
		String text = "text"; //TODO -> validacija
		i.setText(text);
		i.setDuration(0);
		
		List<Instructions_Diet>	curr = di.getInstructions_Diets();
		curr.add(i);
		em.merge(di);
		return new ModelAndView("instructions");
	}
	
	@RequestMapping(value = "/admin/madd/{mid}",  method=RequestMethod.POST)
	public ModelAndView updateMedicineInstructionsPOST(Model model, HttpServletRequest request, @PathVariable("mid") String mid,
			@ModelAttribute("commandiam") @Valid InsAddMedicineForm commandiam) {
		
		TypedQuery<Medicine> qu = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu.setParameter(1, mid).getSingleResult(); 
		
		Instructions i = new Instructions();
		i.setName("Navodilo za "+m.getName());
		String text = "text"; //TODO -> validacija
		i.setText(text);
		i.setDuration(0);
		
		m.addInstruction(i);
		em.merge(m);
		return new ModelAndView("instructions");
	}
}


