package si.fri.t15.admin.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Instructions;
import si.fri.t15.models.Medicine;
import si.fri.t15.validators.InsAddDietForm;
import si.fri.t15.validators.InsAddDiseaseForm;
import si.fri.t15.validators.InsAddMedicineForm;

@Controller
public class InstructionsController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@InitBinder
	protected void initBinder1(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator1);
	}
 
	@Resource(name="insAddDietValidator")
	Validator validator1;
	
	@InitBinder
	protected void initBinder2(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator2);
	}
 
	@Resource(name="insAddDiseaseValidator")
	Validator validator2;
	
	@InitBinder
	protected void initBinder3(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(validator3);
	}
 
	@Resource(name="insAddMedicineValidator")
	Validator validator3;
	
	@RequestMapping(value = "/admin/instructions",  method=RequestMethod.GET)
	public ModelAndView updateInstructionsGET(Model model, HttpServletRequest request) {
		
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
		
		Instructions i = new Instructions();
		i.setName("Navodilo za "+d.getName());
		String text = "text"; //TODO -> validacija
		i.setText(text);
		i.setDuration(0);
		i.setDisease(d);
		em.persist(i);
		
		return new ModelAndView("instructions");
	}
	
	@RequestMapping(value = "/admin/diadd/{diid}",  method=RequestMethod.POST)
	public ModelAndView addDietInstructionsPOST(Model model, HttpServletRequest request, @PathVariable("diid") String diid,
			@ModelAttribute("commandiadi") @Valid InsAddDietForm commandiadi) {
		
		TypedQuery<Diet> qu = em.createNamedQuery("Diet.findDiet", Diet.class);
		Diet di = qu.setParameter(1, diid).getSingleResult();
		
		Instructions i = new Instructions();
		i.setName("Navodilo za "+di.getName());
		String text = "text"; //TODO -> validacija
		i.setText(text);
		i.setDuration(0);
		i.setDiet(di);
		em.persist(i);
		
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
		i.setMedicine(m);
		em.persist(i);
		
		return new ModelAndView("instructions");
	}

}
