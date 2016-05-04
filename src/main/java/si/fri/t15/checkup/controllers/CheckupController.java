package si.fri.t15.checkup.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.dao.CheckupRepository;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;

@Controller
public class CheckupController extends ControllerBase {
	
	@Autowired
	private CheckupRepository cRepo;
	
	@RequestMapping(value = "/checkup/{id}")
	public String checkup(@PathVariable("id") int id,Model model, HttpServletRequest request) {		
		
		Checkup c = (Checkup) cRepo.findCheckup(id);
		PatientData p = cRepo.findPatientByCheckup(id);
		List<DoctorData> ds = cRepo.findDoctorsByCheckup(id);
				
		model.addAttribute("idc", id); 
		model.addAttribute("reason", c.getReason());		
		model.addAttribute("pdata", p); 
		model.addAttribute("ddata", ds); 
		
		/*
		model.addAttribute("diseases", diseases); 
		model.addAttribute("medicines", medicines); 
		model.addAttribute("diets", diets); 
		model.addAttribute("results", results); 
		*/
				
		//Side menu variables
		model.addAttribute("usertype", "admin");
		model.addAttribute("page", "admin");
		model.addAttribute("subpage", "addDoctor");	
		model.addAttribute("path", "/mediko_dev/");
		//Page variables
		model.addAttribute("title", "Pregled");
		return "checkup";
	}
}
