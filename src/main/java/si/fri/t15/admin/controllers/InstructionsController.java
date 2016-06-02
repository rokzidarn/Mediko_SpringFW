package si.fri.t15.admin.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Instructions;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.InsAddDietForm;
import si.fri.t15.validators.InsAddDietValidator;
import si.fri.t15.validators.InsAddDiseaseForm;
import si.fri.t15.validators.InsAddDiseaseValidator;
import si.fri.t15.validators.InsAddMedicineForm;
import si.fri.t15.validators.InsAddMedicineValidator;
import si.fri.t15.validators.InsDelDietForm;
import si.fri.t15.validators.InsDelDietValidator;
import si.fri.t15.validators.InsDelDiseaseForm;
import si.fri.t15.validators.InsDelDiseaseValidator;
import si.fri.t15.validators.InsDelMedicineForm;
import si.fri.t15.validators.InsDelMedicineValidator;

@Controller
public class InstructionsController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	PasswordEncoder passwordEncoder;
 
	@Autowired
	InsAddDiseaseValidator insertDiseaseValidator;
	
	@InitBinder("commandiad")
	protected void initBinderIAD(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertDiseaseValidator);
	}
	
	@Autowired
	InsAddDietValidator insertDietValidator;
	
	@InitBinder("commandiadi")
	protected void initBinderIADI(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertDietValidator);
	}
	
	@Autowired
	InsAddMedicineValidator insertMedicineValidator;
	
	@InitBinder("commandiam")
	protected void initBinderIAM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertMedicineValidator);
	}
	
	@Autowired
	InsDelDietValidator insDelDietValidator;
	
	@InitBinder("commandiddi")
	protected void initBinderIDDI(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insDelDietValidator);
	}
	
	@Autowired
	InsDelDiseaseValidator insDelDiseaseValidator;
	
	@InitBinder("commandidd")
	protected void initBinderIDD(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insDelDiseaseValidator);
	}
	
	@Autowired
	InsDelMedicineValidator insDelMedicineValidator;
	
	@InitBinder("commandidm")
	protected void initBinderIDM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insDelMedicineValidator);
	}

	@RequestMapping(value = "/admin/instructions",  method=RequestMethod.GET)
	public ModelAndView updateInstructionsGET(Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findAll", Disease.class);
		List<Disease> allDiseases = (List<Disease>) qu.getResultList(); 
		
		TypedQuery<Diet> qu2 = em.createNamedQuery("Diet.findAll", Diet.class);
		List<Diet> allDiets = (List<Diet>) qu2.getResultList(); 
		
		TypedQuery<Medicine> qu3 = em.createNamedQuery("Medicine.findAll", Medicine.class);
		List<Medicine> allMedicines = (List<Medicine>) qu3.getResultList();
		
		TypedQuery<Instructions> qu4 = em.createNamedQuery("Instructions.findAll", Instructions.class);
		List<Instructions> allInstructions = (List<Instructions>) qu4.getResultList();
		
		//AJAX		
		TypedQuery<Disease> qux = em.createNamedQuery("Disease.findDisease", Disease.class);
		String gripa = " virus ni ";
		Disease d = qux.setParameter(1, gripa).getSingleResult();
		
		TypedQuery<Diet> qux2 = em.createNamedQuery("Diet.findDiet", Diet.class);
		int celiakija = 1;
		Diet di = qux2.setParameter(1, celiakija).getSingleResult();
		
		TypedQuery<Medicine> qux3 = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		int enalapril = 1;
		Medicine m = qux3.setParameter(1, enalapril).getSingleResult();
		
		List<Instructions> diseaseInstructions = new ArrayList<>();
		List<Instructions> dietInstructions = new ArrayList<>();
		List<Instructions> medicineInstructions = new ArrayList<>();
		for(Instructions icurr : allInstructions){
			if(icurr.getDisease() == d)
				diseaseInstructions.add(icurr);
			else if(icurr.getDiet() == di)
				dietInstructions.add(icurr);
			else if(icurr.getMedicine() == m)
				medicineInstructions.add(icurr);
		}
		
		model.addAttribute("insDiseases", diseaseInstructions);
		model.addAttribute("insDiets", dietInstructions);
		model.addAttribute("insMedicines", medicineInstructions);
		//KONEC
				
		model.addAttribute("user", userSession);
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
	
	
	@Transactional
	@RequestMapping(value = "/admin/dadd",  method=RequestMethod.POST)
	public String addDiseaseInstructionsPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandiad") @Valid InsAddDiseaseForm commandiad) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, commandiad.getDisease()).getSingleResult();
		
		Instructions i = new Instructions();
		i.setName("Navodilo za "+d.getName());
		String text = commandiad.getInstruction();
		i.setText(text);
		i.setDuration(0);
		i.setDisease(d);
		em.persist(i);
		
		return "redirect:/admin/instructions";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/diadd",  method=RequestMethod.POST)
	public String addDietInstructionsPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandiadi") @Valid InsAddDietForm commandiadi) {
		
		TypedQuery<Diet> qu = em.createNamedQuery("Diet.findDiet", Diet.class);
		Diet di = qu.setParameter(1, commandiadi.getDiet()).getSingleResult();
		
		Instructions i = new Instructions();
		i.setName("Navodilo za "+di.getName());
		String text = commandiadi.getInstruction_diet();
		i.setText(text);
		i.setDuration(0);
		i.setDiet(di);
		em.persist(i);
		
		return "redirect:/admin/instructions";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/madd",  method=RequestMethod.POST)
	public String addMedicineInstructionsPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandiam") @Valid InsAddMedicineForm commandiam) {
		
		TypedQuery<Medicine> qu = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu.setParameter(1, commandiam.getMedicine()).getSingleResult(); 
		
		Instructions i = new Instructions();
		i.setName("Navodilo za "+m.getName());
		String text = commandiam.getInstruction_medicine();
		i.setText(text);
		i.setDuration(0);
		i.setMedicine(m);
		em.persist(i);
		
		return "redirect:/admin/instructions";
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------
	
	@Transactional
	@RequestMapping(value = "/admin/ddel",  method=RequestMethod.POST)
	public String delDiseaseInstructionsPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandidd") @Valid InsDelDiseaseForm commandidd) {
		
		TypedQuery<Instructions> qu = em.createNamedQuery("Instructions.findInstructions", Instructions.class);
		Instructions i = qu.setParameter(1, commandidd.getId()).getSingleResult(); 
		
		em.remove(i);
		
		return "redirect:/admin/instructions";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/didel",  method=RequestMethod.POST)
	public String delDiseaseInstructionsPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandiddi") @Valid InsDelDietForm commandiddi) {
		
		TypedQuery<Instructions> qu = em.createNamedQuery("Instructions.findInstructions", Instructions.class);
		Instructions i = qu.setParameter(1, commandiddi.getId()).getSingleResult(); 
		
		em.remove(i);
		
		return "redirect:/admin/instructions";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/mdel",  method=RequestMethod.POST)
	public String delDiseaseInstructionsPOST(Model model, HttpServletRequest request,
			@ModelAttribute("commandidm") @Valid InsDelMedicineForm commandidm) {
		
		TypedQuery<Instructions> qu = em.createNamedQuery("Instructions.findInstructions", Instructions.class);
		Instructions i = qu.setParameter(1, commandidm.getId()).getSingleResult(); 
		
		em.remove(i);
		
		return "redirect:/admin/instructions";
	}

}
