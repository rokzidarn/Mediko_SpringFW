package si.fri.t15.checkup.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.servlet.ModelAndView;
import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.Reading;
import si.fri.t15.models.Reading_Data;
import si.fri.t15.models.Result_Checkup;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.InsertDiagnosisForm;
import si.fri.t15.validators.InsertDiagnosisValidator;
import si.fri.t15.validators.InsertDietForm;
import si.fri.t15.validators.InsertDietValidator;
import si.fri.t15.validators.InsertDiseaseForm;
import si.fri.t15.validators.InsertDiseaseValidator;
import si.fri.t15.validators.InsertMedicineForm;
import si.fri.t15.validators.InsertMedicineValidator;
import si.fri.t15.validators.InsertReasonForm;
import si.fri.t15.validators.InsertReasonValidator;
import si.fri.t15.validators.InsertResultForm;
import si.fri.t15.validators.InsertResultValidator;

@Controller
public class CheckupController extends ControllerBase {
	
	@Autowired
	InsertReasonValidator insertReasonValidator;
	
	@InitBinder("commandr")
	protected void initBinderR(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertReasonValidator);
	}
	
	@Autowired
	InsertDiseaseValidator insertDiseaseValidator;
	
	@InitBinder("commandd")
	protected void initBinderD(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertDiseaseValidator);
	}
	
	@Autowired
	InsertDietValidator insertDietValidator;
	
	@InitBinder("commanddi")
	protected void initBinderDI(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertDietValidator);
	}
	
	@Autowired
	InsertMedicineValidator insertMedicineValidator;
	
	@InitBinder("commanddm")
	protected void initBinderM(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertMedicineValidator);
	}
	
	@Autowired
	InsertDiagnosisValidator insertDiagnosisValidator;
	
	@InitBinder("commanddg")
	protected void initBinderDG(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertDiagnosisValidator);
	}
	
	@Autowired
	InsertResultValidator insertResultValidator;
	
	@InitBinder("commandrs")
	protected void initBinderRS(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.setValidator(insertResultValidator);
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}")
	public String checkup(@PathVariable("id") int id,Model model, HttpServletRequest request,  @AuthenticationPrincipal User userSession) {		
		
		if(userSession.getData().getClass().equals(PatientData.class) && userSession.getSelectedPatient() == null){
			userSession.setSelectedPatient((PatientData)userSession.getData());
		}
		
		//Side menu variables
		String userType = "user";
		User user = em.merge(userSession);
		PatientData p = em.merge(userSession.getSelectedPatient());
		
		Checkup curr = null;
		
		for(Checkup c : p.getCheckups()){
			if(c.getId() == id){
				curr = c;
				break;
			}
		}
		
		DoctorData d = curr.getDoctor();
				
		model.addAttribute("idc", id); 
		model.addAttribute("instructions", curr.getInstructions());
		model.addAttribute("reason", curr.getReason());	
		model.addAttribute("date", curr.getAppointment().getDate());	
		model.addAttribute("pdata", p); 
		model.addAttribute("ddata", d); 
				
		model.addAttribute("diseases", curr.getDiseases()); 
		model.addAttribute("diets", curr.getDiets()); 
		model.addAttribute("medicines", curr.getMedicines());
		model.addAttribute("rcc", curr.getResultCheckups());
		
		//Side menu variables
		model.addAttribute("usertype", userType);
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("page", "checkup");
		//Page variables
		model.addAttribute("title", "PREGLED");
		model.addAttribute("user", user);
		return "checkup";
	}
	
	@Transactional
	@RequestMapping(value = "/appointment/{id}", method=RequestMethod.POST)
	public ModelAndView appointmentCheckup(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		TypedQuery<Appointment> qu = em.createNamedQuery("Appointment.findAppointment", Appointment.class);
		Appointment curr = qu.setParameter(1, id).getSingleResult(); //dobimo appointment, ki je bil izbran
		
		TypedQuery<Checkup> qu2 = em.createNamedQuery("Checkup.findCheckupByAppointment", Checkup.class);
		List <Checkup> c = (List<Checkup>) qu2.setParameter(1, id).getResultList();
		if(c.isEmpty()){
		
			Checkup recent = new Checkup();
			recent.setAppointment(curr);
			recent.setDoctor(curr.getDoctor());
			recent.setPatient(curr.getPatient());
			recent.setReason("/");
			recent.setInstructions("/");
			
			em.persist(recent); //ustvarimo checkup, ki se ustvari iz podatkov appointmenta
			
			TypedQuery<Checkup> qu3 = em.createNamedQuery("Checkup.findCheckupByAppointment", Checkup.class);
			Checkup checkupc = qu3.setParameter(1, id).getSingleResult();
			int idc = checkupc.getCheckupId(); //dobimo id novo nastalega checkupa, naredi se preusmeritev
			
			return new ModelAndView("redirect:/checkup/"+idc+"/insert");
		}
		else{
			TypedQuery<Checkup> qu4 = em.createNamedQuery("Checkup.findCheckupByAppointment", Checkup.class);
			Checkup checkup = qu4.setParameter(1, id).getSingleResult();
			return new ModelAndView("redirect:/checkup/"+checkup.getCheckupId()+"/insert");
		}
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/insert", method=RequestMethod.GET)
	public ModelAndView checkupInsertGet(@PathVariable("id") int id,Model model, HttpServletRequest request, @AuthenticationPrincipal User userSession) {
		
		String userType = "user";
		User user = em.merge(userSession);
		DoctorData doctor = (DoctorData)em.merge(userSession.getData());
		
		//trenutni podatki o pregledu, kar je vpisano
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		DoctorData d = curr.getDoctor();
		PatientData p = curr.getPatient();
		
		model.addAttribute("idc", id); 
		model.addAttribute("ddata", d); 
		model.addAttribute("pdata", p); 
		model.addAttribute("reason", curr.getReason()); 
		model.addAttribute("diagnosis", curr.getInstructions()); 
		
		model.addAttribute("diseases", curr.getDiseases()); 
		model.addAttribute("diets", curr.getDiets()); 
		model.addAttribute("medicines", curr.getMedicines());
		
		List<Result_Checkup> rcc = curr.getResultCheckups();
		model.addAttribute("rcc", rcc);
		
		//vse možne bolezni, zdravila, diete iz baze, iz česar bo izbiral zdravnik DDL
		TypedQuery<Disease> qud = em.createNamedQuery("Disease.findAll", Disease.class);
		List<Disease> idiseases = qud.getResultList();		
		model.addAttribute("idiseases", idiseases);
		
		TypedQuery<Diet> qudi = em.createNamedQuery("Diet.findAll", Diet.class);
		List<Diet> idiets = qudi.getResultList();		
		model.addAttribute("idiets", idiets);
		
		TypedQuery<Medicine> qum = em.createNamedQuery("Medicine.findAll", Medicine.class);
		List<Medicine> imedicines = qum.getResultList();		
		model.addAttribute("imedicines", imedicines);
		
		model.addAttribute("selectedPatient", userSession.getSelectedPatient());
		model.addAttribute("isDoctor", true);
		model.addAttribute("page", "home");
		model.addAttribute("title", "NADZORNA PLOŠČA MEDIKO");
		model.addAttribute("user", user);
		model.addAttribute("usertype", userType);
		
		return new ModelAndView("checkupInsert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/reason", method=RequestMethod.POST)
	public ModelAndView insertReason(@PathVariable("id") int id, @ModelAttribute("commandr") @Valid InsertReasonForm commandr, Model model, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		String reason = commandr.getReason();
		curr.setReason(reason);
		em.merge(curr);
		
		return new ModelAndView("redirect:/checkup/"+id+"/insert");
	}
	
	@Transactional //params = "insertDisease"
	@RequestMapping(value = "/checkup/{id}/disease", method=RequestMethod.POST) //gumb redirecta sem, kjer se vstavlja
	public ModelAndView insertDisease(@PathVariable("id") int id, @RequestParam("idisease") String idd ,@ModelAttribute("commandd") @Valid InsertDiseaseForm commandd, Model model, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		TypedQuery<Disease> qud = em.createNamedQuery("Disease.findDisease", Disease.class);
		Disease idisease = qud.setParameter(1, idd).getSingleResult();				
		List<Disease> diseases = curr.getDiseases();
		diseases.add(idisease);
		
		curr.setDiseases(diseases); //dodajanje bolezni na checkup
		em.merge(curr);
		
		return new ModelAndView("redirect:/checkup/"+id+"/insert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/diet", method=RequestMethod.POST) //params = "insertDiet"
	public ModelAndView insertDiet(@PathVariable("id") int id, @RequestParam("idiet") int iddi, @ModelAttribute("commanddi") @Valid InsertDietForm commanddi, Model model, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		qu.setParameter(1,id);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		TypedQuery<Diet> qud = em.createNamedQuery("Diet.findDiet", Diet.class);
		Diet idiet = qud.setParameter(1, iddi).getSingleResult();		
		List<Diet> diets = curr.getDiets();
		diets.add(idiet);
		
		curr.setDiets(diets);
		em.merge(curr);
		
		return new ModelAndView("redirect:/checkup/"+id+"/insert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/medicine", method=RequestMethod.POST) //params = "insertMedicine"
	public ModelAndView insertMedicine(@PathVariable("id") int id, @RequestParam("imedicine") int idm, @ModelAttribute("commandm") @Valid InsertMedicineForm commandm, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		qu.setParameter(1,id);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		TypedQuery<Medicine> qud = em.createNamedQuery("Medicine.findMedicine", Medicine.class);
		Medicine imedicine = qud.setParameter(1, idm).getSingleResult();		
		List<Medicine> medicines = curr.getMedicines();
		medicines.add(imedicine);
		
		curr.setMedicines(medicines); 
		em.merge(curr);
		
		return new ModelAndView("redirect:/checkup/"+id+"/insert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/results", method=RequestMethod.POST)
	public ModelAndView insertResult(@PathVariable("id") int id, @RequestParam("iresult") String r, @RequestParam("itype") String type, @RequestParam("itext") String text, @ModelAttribute("commandrs") @Valid InsertResultForm commandrs, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		qu.setParameter(1,id);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		Date today = new Date(Calendar.getInstance().getTime().getTime());
		
		Reading read = new Reading();
		read.setType(type);
		read.setDate(today);
		em.persist(read); //kreiranje novega readinga
		//v bazi vidim da ni povezave na reading_data a v jpa je, se tisti pobriše?
		
		Result_Checkup rc = new Result_Checkup();
		rc.setCheckup(curr);
		rc.setText(text);
		rc.setType(type);
		rc.setValue("0");
		em.persist(rc);
		//v result_checkup je redundanten atribut value, ker se piše v reading_data!
		
		Reading_Data rd = new Reading_Data();
		rd.setData(r);
		if(type.equals("Krvni tlak")){
			rd.setUnit("sbp/dbp");
		}
		else if(type.equals("Glukoza")){
			rd.setUnit("g/mmol");
		}
		rd.setReading(read);
		rd.setResult_Checkup(rc);
		em.persist(rd);
		
		return new ModelAndView("redirect:/checkup/"+id+"/insert");
	}
	
	@Transactional
	@RequestMapping(value = "/checkup/{id}/diagnosis", method=RequestMethod.POST)
	public ModelAndView insertDiagnosis(@PathVariable("id") int id, @RequestParam("idiagnosis") String dtext, @ModelAttribute("commanddg") @Valid InsertDiagnosisForm commanddg, HttpServletRequest request) {
		TypedQuery<Checkup> qu = em.createNamedQuery("Checkup.findCheckup", Checkup.class);
		qu.setParameter(1,id);
		Checkup curr = qu.setParameter(1, id).getSingleResult();
		
		curr.setInstructions(dtext);		
		em.merge(curr);
		
		return new ModelAndView("redirect:/checkup/"+id+"/insert");
	}
}
