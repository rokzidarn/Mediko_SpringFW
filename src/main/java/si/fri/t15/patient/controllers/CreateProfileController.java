package si.fri.t15.patient.controllers;

import java.sql.Date;
import java.util.Calendar;
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
import si.fri.t15.validators.CreatePatientValidator;
import si.fri.t15.validators.PatientProfileForm;

@Controller
public class CreateProfileController extends ControllerBase{
	
	@Autowired
	CreatePatientValidator createPatientValidator;
	
	@InitBinder("command")
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(createPatientValidator);
	}
 
	@Transactional
	@RequestMapping(value = "/createProfile", method=RequestMethod.GET)
	public String createPatient(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
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
		model.addAttribute("page", "home");
		//Page variables
		model.addAttribute("title", "Uredi profil");
		
		
		//Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes",poBoxes);
		model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
		
		return "createPatient";
	}
	
	@RequestMapping(value = "/createProfile", method=RequestMethod.POST)
	@Transactional
	public String createPatientPOST(Model model, @ModelAttribute("command") @Valid PatientProfileForm command,
			BindingResult result, HttpServletRequest request, @AuthenticationPrincipal User user) {
		
		
		//Side menu variables
		String userType = "user";
		//user = em.merge(user);
		if(UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		model.addAttribute("user",user);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "home");
		model.addAttribute("subpage", "createPatient");	
		//Page variables
		model.addAttribute("title", "Dodaj pacienta");
		
		//Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes",poBoxes);
		model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
		
		if (result.hasErrors()) {
			return "createPatient";
		}
			
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
		
		patient.setPhoneNumber(command.getPhoneNumber());
		
		em.persist(patient);
		
		user.setData(patient);
		
		em.merge(user);
		
		return "redirect:/dashboard";
	
	}
	
	@RequestMapping(value="/updateProfile", method=RequestMethod.GET)
	public String updateProfileGET(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {

		// Side menu variables
		String userType = "user";
		if (UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		
		PatientData pData;
		if (user.getSelectedPatient() != null) {
			pData = user.getSelectedPatient();
		}
		else {
			pData = (PatientData) user.getData();
		}
		model.addAttribute("user", user);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "home");
		// Page variables
		model.addAttribute("title", "Uredi profil");
		
		// Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes", poBoxes);
		model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
		
		model.addAttribute("pData", pData);

		return "updatePatient";
	}
	
	@Transactional
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String updateProfilePOST(Model model, @ModelAttribute("command") @Valid PatientProfileForm command,
			BindingResult result, HttpServletRequest request, @AuthenticationPrincipal User user) {
		
		String userType = "user";
		if (UserType.ADMIN.equals(user.getUserType())) {
			userType = "admin";
		}
		
		PatientData pData;
		if (user.getSelectedPatient() != null) {
			pData = user.getSelectedPatient();
		}
		else {
			pData = (PatientData) user.getData();
		}
		model.addAttribute("user", user);
		model.addAttribute("usertype", userType);
		model.addAttribute("page", "home");
		// Page variables
		model.addAttribute("title", "Uredi profil");
		
		// Post office numbers;
		Query allPOBoxQuery = em.createNamedQuery("PO_Box.findAll");
		List<PO_Box> poBoxes = allPOBoxQuery.getResultList();
		model.addAttribute("po_boxes", poBoxes);
		model.addAttribute("relationshipTypes", UserData.getRelationshipTypes());
		
		model.addAttribute("pData", pData);
		
		if(result.hasErrors()) {
			return "updatePatient";
		}
		
		pData.setAddress(command.getAddress());
		pData.setBirth_date(Date.valueOf(command.getBirth()));
		pData.setCardNumber(command.getCardNumber());
		pData.setFirst_name(command.getFirstName());
		pData.setLast_name(command.getLastName());
		pData.setPo_number(command.getPobox());
		pData.setSex(command.getSex());
		pData.setPhoneNumber(command.getPhoneNumber());
		
		EmergencyContactData eData = (pData.getEmergencyContactData() == null) ? new EmergencyContactData()
				: pData.getEmergencyContactData();
		eData.setAddress(command.getContactAddress());
		eData.setFirst_name(command.getContactFirstName());
		eData.setLast_name(command.getContactLastName());
		eData.setPhoneNumber(command.getContactPhoneNumber());
		eData.setRelationshipType(command.getContactRelationship());

		if (pData.getEmergencyContactData() == null) {
			em.persist(eData);
			pData.setEmergencyContactData(eData);
		} else {
			em.merge(eData);
		}
		
		em.merge(pData);

		return "redirect:/dashboard";
	}

}
