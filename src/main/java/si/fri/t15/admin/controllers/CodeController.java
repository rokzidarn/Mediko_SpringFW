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
import si.fri.t15.validators.CreateMedicalWorkerForm;
import si.fri.t15.validators.CreateMedicalWorkerValidator;

@Controller
public class CodeController extends ControllerBase{
	@Autowired
	EntityManager em;
	
	@Autowired
	CreateMedicalWorkerValidator createMedicalWorkerValidator;
	
	@InitBinder("command")
	protected void initBinderCMW(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(createMedicalWorkerValidator);
	}
	
	@RequestMapping(value = "/admin/code",  method=RequestMethod.GET)
	public ModelAndView codesGET(Model model, HttpServletRequest request) {
		
		TypedQuery<PO_Box> qu = em.createNamedQuery("PO_Box.findAll", PO_Box.class);
		List<PO_Box> pos = (List<PO_Box>) qu.getResultList(); 
		
		model.addAttribute("pos", pos);
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		model.addAttribute("title", "Šifranti");
		
		return new ModelAndView("codes");
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/disease/add",  method=RequestMethod.POST)
	public ModelAndView addDiseasePOST(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		
		Disease d = new Disease();
		d.setDiseaseId("asd");
		d.setIsAllergy(0);
		d.setName("asdasdasd");
		
		em.persist(d);		
		return new ModelAndView("codes");
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/diet/add",  method=RequestMethod.POST)
	public ModelAndView addDietPOST(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		
		Diet d = new Diet();
		d.setName("asdasdasd");
		
		em.persist(d);	
		return new ModelAndView("codes");
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/medicine/add",  method=RequestMethod.POST)
	public ModelAndView addMedicinePOST(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		
		Medicine m = new Medicine();
		m.setName("asdasd2");
		m.setType('m');
		m.setLink("asdasasd");
		
		em.persist(m);		
		return new ModelAndView("codes");
	}
	
	@Transactional
	@RequestMapping(value = "/admin/code/medical_center/add",  method=RequestMethod.POST)
	public ModelAndView addMedicalCenterPOST(Model model, @ModelAttribute("command") @Valid CreateMedicalWorkerForm command,
			BindingResult result, HttpServletRequest request) {
		
		Medical_Center mc = new Medical_Center();
		mc.setName("asdasd");
		
		TypedQuery<PO_Box> qu = em.createNamedQuery("PO_Box.findPOBox", PO_Box.class);
		PO_Box po = qu.setParameter(1, 8270).getSingleResult();
		mc.setPo_box(po);
		
		em.persist(mc);		
		return new ModelAndView("codes");
	}
}
