package si.fri.t15.patient.controllers;

import java.sql.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import si.fri.t15.base.models.UserData;
import si.fri.t15.models.PO_Box;
import si.fri.t15.models.user.EmergencyContactData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.models.user.User.UserType;
import si.fri.t15.validators.PatientProfileForm;
import si.fri.t15.validators.CreatePatientValidator;

@Controller
public class CreatePatientController extends ControllerBase{
	
	@Autowired
	CreatePatientValidator createPatientValidator;
	
	@InitBinder("command")
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(createPatientValidator);
	}
 
	@Transactional
	@RequestMapping(value = "/patient/createPatient", method=RequestMethod.GET)
	public ModelAndView createPatient(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
		//model.addAttribute("login", "login");
		//model.addAttribute("_csrf", (CsrfToken) request.getAttribute(CsrfToken.class.getName()));
		//model.addAttribute("trans", getTranslation("login.title", request));
		
		//Side menu variables
		String userType = "user";
		user = em.merge(user);
		if(UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		model.addAttribute("user",user);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "patient");
		model.addAttribute("subpage", "createPatient");	
		//Page variables
		model.addAttribute("title", "Dodaj pacienta");
		
		
		//Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes",poBoxes);
		model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
		
		return new ModelAndView( "createPatient");
	}
	
	@RequestMapping(value = "/patient/createPatient", method=RequestMethod.POST)
	@Transactional
	public ModelAndView createPatientPOST(Model model, @ModelAttribute("command") @Valid PatientProfileForm command,
			BindingResult result, HttpServletRequest request, @AuthenticationPrincipal User user) {
		
		
		//Side menu variables
		String userType = "user";
		user = em.merge(user);
		if(UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		model.addAttribute("user",user);
		model.addAttribute("usertype", userType);
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
		
		
		EmergencyContactData eData = new EmergencyContactData();
		eData.setAddress(command.getContactAddress());
		eData.setFirst_name(command.getContactFirstName());
		eData.setLast_name(command.getContactLastName());
		eData.setPhoneNumber(command.getContactPhoneNumber());
		
		em.persist(eData);
		//Dodajanje pacienta
		
		//Create patient
		PatientData patient = new PatientData();
		patient.setCardNumber(command.getCardNumber());
		patient.setEmergencyContactData(eData);
		patient.setFirst_name(command.getFirstName());
		patient.setLast_name(command.getLastName());
		patient.setSex(command.getSex());
		patient.setBirth_date(Date.valueOf(command.getBirth()));
		patient.setPhoneNumber(command.getPhoneNumber());
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
