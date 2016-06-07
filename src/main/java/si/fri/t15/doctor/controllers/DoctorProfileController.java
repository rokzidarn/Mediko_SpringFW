package si.fri.t15.doctor.controllers;

import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
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

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Medical_Center;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.DoctorProfileValidator;

@Controller
public class DoctorProfileController extends ControllerBase {

	@Autowired
	DoctorProfileValidator doctorProfileValidator;

	@InitBinder("command")
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.setValidator(doctorProfileValidator);
	}

	@RequestMapping(value = "/updateDoctorProfile", method = RequestMethod.GET)
	public String updateDoctorProfileGET(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
		String userType = "user";
		model.addAttribute("page", "home");
		model.addAttribute("title", "Urejanje profila");
		model.addAttribute("user", user);
		model.addAttribute("dData", (DoctorData) user.getData());
		TypedQuery<Medical_Center> query = em.createNamedQuery("Medical_Center.findAll", Medical_Center.class);
		model.addAttribute("medicalCenters", query.getResultList());
		model.addAttribute("usertype", userType);
		return "updateDoctor";
	}

	@Transactional
	@RequestMapping(value = "/updateDoctorProfile", method = RequestMethod.POST)
	public String updateDoctorProfilePOST(Model model, HttpServletRequest request, @AuthenticationPrincipal User user,
			@ModelAttribute("command") @Valid DoctorProfileForm command, BindingResult result) {

		if (result.hasErrors()) {
			String userType = "user";
			model.addAttribute("page", "home");
			model.addAttribute("title", "Urejanje profila");
			model.addAttribute("user", user);
			model.addAttribute("dData", (DoctorData) user.getData());
			TypedQuery<Medical_Center> query = em.createNamedQuery("Medical_Center.findAll", Medical_Center.class);
			model.addAttribute("medicalCenters", query.getResultList());
			model.addAttribute("usertype", userType);
			return "updateDoctor";
		}

		DoctorData dData = (DoctorData) user.getData();
		if (dData == null) {
			dData = new DoctorData();
		}

		dData.setFirst_name(command.getFirst_name());
		dData.setLast_name(command.getLast_name());
		dData.setMaxPatients(command.getMaxPatients());
		dData.setPhoneNumber(command.getPhoneNumber());
		dData.setSizz(command.getSizz());
		dData.setType(command.getTitle());

		if (user.getData() == null) {
			em.persist(dData);
			user.setData(dData);
		} else {
			em.merge(dData);
		}

		em.merge(user);

		return "redirect:/dashboard/patient/";
	}
}
