package si.fri.t15.patient.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import si.fri.t15.models.PO_Box;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.CreatePatientForm;
import si.fri.t15.validators.CreatePatientValidator;

@Controller
public class CreatePatientController extends ControllerBase{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	CreatePatientValidator createPatientValidator;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(createPatientValidator);
	}
 
	
	@RequestMapping(value = "/patient/createPatient", method=RequestMethod.GET)
	public ModelAndView createPatient(Model model, HttpServletRequest request) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//Side menu variables
		model.addAttribute("usertype", "user");
		model.addAttribute("page", "patient");
		model.addAttribute("subpage", "createPatient");	
		//Page variables
		model.addAttribute("title", "Dodaj pacienta");
		
		
		//Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes",poBoxes);
		
		return new ModelAndView( "createPatient");
	}
	
	@RequestMapping(value = "/patient/createPatient", method=RequestMethod.POST)
	@Transactional
	public ModelAndView createPatientPOST(Model model, @ModelAttribute("command") @Valid CreatePatientForm command,
			BindingResult result, HttpServletRequest request) {
		
		
		//Side menu variables
		
		model.addAttribute("usertype", "user");
		model.addAttribute("page", "patient");
		model.addAttribute("subpage", "createPatient");	
		//Page variables
		model.addAttribute("title", "Dodaj pacienta");
		
		//Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes",poBoxes);
		
		
		if (result.hasErrors()) {
			return new ModelAndView("createPatient");
		}
		
		//Dodajanje pacienta
		//Get logged in user
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = em.merge(user);
		
		//Create patient
		PatientData patient = new PatientData();
		patient.setFirst_name(command.getFirstName());
		patient.setLast_name(command.getLastName());
		patient.setSex(command.getSex());
		//Birth
		String[]dateValues = command.getBirth().split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(dateValues[0]),Integer.parseInt(dateValues[0]),Integer.parseInt(dateValues[0]));
		patient.setBirth_date(new Date(calendar.getTimeInMillis()));
		
		patient.setAddress(command.getAddress());
		
		//POBOX
		PO_Box pobox = em.find(PO_Box.class, command.getPobox());
		patient.setPo_box(pobox);
		
		PatientData caretakerPatient = (PatientData)user.getData();
		
		//Care taker
		patient.setCaretaker(caretakerPatient);
		
		//Save
		em.persist(patient);
		
		
		return new ModelAndView("createPatient");
	
	}

}
