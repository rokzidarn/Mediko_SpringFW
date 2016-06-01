package si.fri.t15.admin.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medical_Center;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.PO_Box;
import si.fri.t15.validators.AddDietForm;
import si.fri.t15.validators.AddDietValidator;
import si.fri.t15.validators.AddDiseaseForm;
import si.fri.t15.validators.AddDiseaseValidator;
import si.fri.t15.validators.AddMedCenterForm;
import si.fri.t15.validators.AddMedCenterValidator;
import si.fri.t15.validators.AddMedicineForm;
import si.fri.t15.validators.AddMedicineValidator;
import si.fri.t15.validators.DeleteDietForm;
import si.fri.t15.validators.DeleteDietValidator;
import si.fri.t15.validators.DeleteDiseaseForm;
import si.fri.t15.validators.DeleteDiseaseValidator;
import si.fri.t15.validators.DeleteMedCenterForm;
import si.fri.t15.validators.DeleteMedCenterValidator;
import si.fri.t15.validators.DeleteMedicineForm;
import si.fri.t15.validators.DeleteMedicineValidator;

@Controller
public class CodeController extends ControllerBase{
	@Autowired
	EntityManager em;
	
	@Autowired
	AddDiseaseValidator addDiseaseValidator;
	
	@InitBinder("commandda")
	protected void initBinderAD(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(addDiseaseValidator);
	}
	
	@Autowired
	AddDietValidator adDietValidator;
	
	@InitBinder("commanddia")
	protected void initBinderADI(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(adDietValidator);
	}
	
	@Autowired
	AddMedCenterValidator addMedCenterValidator;
	
	@InitBinder("commandmca")
	protected void initBinderAMC(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(addMedCenterValidator);
	}
	
	@Autowired
	AddMedicineValidator addMedicineValidator;
	
	@InitBinder("commandma")
	protected void initBinderAM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(addMedicineValidator);
	}
	
	@Autowired
	DeleteDiseaseValidator deleteDiseaseValidator;
	
	@InitBinder("commanddd")
	protected void initBinderDD(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(deleteDiseaseValidator);
	}
	
	@Autowired
	DeleteDietValidator deleteDietValidator;
	
	@InitBinder("commandid")
	protected void initBinderDDI(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(deleteDietValidator);
	}
	
	@Autowired
	DeleteMedicineValidator deleteMedicineValidator;
	
	@InitBinder("commandmd")
	protected void initBinderDM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(deleteMedicineValidator);
	}
	
	@Autowired
	DeleteMedCenterValidator deleteMedCenterValidator;
	
	@InitBinder("commandmcd")
	protected void initBinderDMC(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(deleteMedCenterValidator);
	}
	
	@RequestMapping(value = "/admin/code",  method=RequestMethod.GET)
	public ModelAndView codesGET(Model model, HttpServletRequest request) {
		
		TypedQuery<PO_Box> qu = em.createNamedQuery("PO_Box.findAll", PO_Box.class);
		List<PO_Box> pos = (List<PO_Box>) qu.getResultList(); 
		
		TypedQuery<Disease> qu2 = em.createNamedQuery("Disease.findAll", Disease.class);
		List<Disease> diseases = (List<Disease>) qu2.getResultList();
		
		TypedQuery<Diet> qu3 = em.createNamedQuery("Diet.findAll", Diet.class);
		List<Diet> diets = (List<Diet>) qu3.getResultList();
		
		TypedQuery<Medicine> qu4 = em.createNamedQuery("Medicine.findAll", Medicine.class);
		List<Medicine> medicines = (List<Medicine>) qu4.getResultList();
		
		TypedQuery<Medical_Center> qu5 = em.createNamedQuery("Medical_Center.findAll", Medical_Center.class);
		List<Medical_Center> mcs = (List<Medical_Center>) qu5.getResultList();
		
		model.addAttribute("pos", pos);
		model.addAttribute("diseases", diseases);
		model.addAttribute("diets", diets);
		model.addAttribute("medicines", medicines);
		model.addAttribute("mcs", mcs);
		
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Šifranti");
		
		return new ModelAndView("codes");
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/disease/add",  method=RequestMethod.POST)
	public String addDiseasePOST(Model model, @ModelAttribute("commandda") @Valid AddDiseaseForm commandda,
			BindingResult result, HttpServletRequest request) {
		
		Disease d = new Disease();
		d.setDiseaseId(commandda.getDid());
		d.setIsAllergy(commandda.getDtype());
		d.setName(commandda.getDname());
		
		em.persist(d);		
		return "redirect:/admin/code";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/diet/add",  method=RequestMethod.POST)
	public String addDietPOST(Model model, @ModelAttribute("commandia") @Valid AddDietForm commandia,
			BindingResult result, HttpServletRequest request) {
		
		Diet d = new Diet();
		d.setName(commandia.getDiname());
		
		em.persist(d);	
		return "redirect:/admin/code";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/medicine/add",  method=RequestMethod.POST)
	public String addMedicinePOST(Model model, @ModelAttribute("commandma") @Valid AddMedicineForm commandma,
			BindingResult result, HttpServletRequest request) {
		
		Medicine m = new Medicine();
		m.setName(commandma.getMname());
		m.setType('m');
		m.setLink("https://en.wikipedia.org/wiki/Medicine");
		
		em.persist(m);		
		return "redirect:/admin/code";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/medical_center/add",  method=RequestMethod.POST)
	public String addMedicalCenterPOST(Model model, @ModelAttribute("commandmca") @Valid AddMedCenterForm commandmca,
			BindingResult result, HttpServletRequest request) {
		
		Medical_Center mc = new Medical_Center();
		mc.setName(commandmca.getMcname());
		
		TypedQuery<PO_Box> qu = em.createNamedQuery("PO_Box.findPOBox", PO_Box.class);
		PO_Box po = qu.setParameter(1, commandmca.getPid()).getSingleResult();
		mc.setPo_box(po);
		
		em.persist(mc);		
		return "redirect:/admin/code";
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	@Transactional
	@RequestMapping(value = "/admin/code/disease/del",  method=RequestMethod.POST)
	public String deleteDiseasetPOST(Model model, @ModelAttribute("commanddd") @Valid DeleteDiseaseForm commanddd,
			BindingResult result, HttpServletRequest request) {
		
		TypedQuery<Disease> qu = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease d = qu.setParameter(1, commanddd.getIdd()).getSingleResult();
		
		//TODO: set
		
		em.merge(d);
		return "redirect:/admin/code";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/diet/del",  method=RequestMethod.POST)
	public String deleteDietPOST(Model model, @ModelAttribute("commandid") @Valid DeleteDietForm commandid,
			BindingResult result, HttpServletRequest request) {
		
		TypedQuery<Diet> qu = em.createNamedQuery("Diet.findDiet", Diet.class);
		Diet d = qu.setParameter(1, commandid.getIddi()).getSingleResult();
		
		//TODO: set
		
		em.merge(d);
		return "redirect:/admin/code";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/medicine/del",  method=RequestMethod.POST)
	public String deleteMedicinePOST(Model model, @ModelAttribute("commandmd") @Valid DeleteMedicineForm commandmd,
			BindingResult result, HttpServletRequest request) {
		
		TypedQuery<Medicine> qu = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine m = qu.setParameter(1, commandmd.getIdm()).getSingleResult();
		
		//TODO: set
		
		em.merge(m);
		return "redirect:/admin/code";
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/medical_center/del",  method=RequestMethod.POST)
	public String deleteMedicalCenterPOST(Model model, @ModelAttribute("commandmcd") @Valid DeleteMedCenterForm commandmcd,
			BindingResult result, HttpServletRequest request) {
		
		TypedQuery<Medical_Center> qu = em.createNamedQuery("Medical_Center.findMedical_Center", Medical_Center.class);
		Medical_Center m = qu.setParameter(1, commandmcd.getIdmc()).getSingleResult();
		
		//TODO: set
		
		em.merge(m);
		return "redirect:/admin/code";
	}
}
