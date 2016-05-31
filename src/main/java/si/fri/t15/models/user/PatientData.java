package si.fri.t15.models.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

import si.fri.t15.base.models.UserData;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Diet;
import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.PO_Box;
import si.fri.t15.models.Result_Checkup;

@Entity
public class PatientData extends UserData {

	private static final long serialVersionUID = 1L;

	private static final boolean Checkup = false;
	
	public enum Relationship {
		SIBLING, PARENT, GRANDPARENT, FOSTER_PARENT, SIGNIFICANT_OTHER, CARETAKER
	}
	
	@ManyToOne
	private PatientData caretaker;
	
	@ManyToOne
	private DoctorData doctor;
	
	@ManyToOne
	private DoctorData dentist;
	
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointments;
	
	private int po_number;
	
	@OneToOne
	private PO_Box po_box;
	
	@OneToMany(mappedBy="patient")
	private List<Checkup> checkups;
	
	@OneToMany(mappedBy="caretaker")
	private List<PatientData> patients;
	
	@Column(name="Address", length=100, nullable=false, updatable=true)
	private String address;
	
	@Column(name="Birth_date",nullable=false, updatable=true)
	private Date birth_date;
	
	@Column(name="Sex",  nullable=false, updatable=true)
	private char sex;
	
	@Column( nullable=false, updatable=true)
	private String cardNumber;
	
	@Transient
	private List<Medicine> medicines;	
	
	public List<Medicine> getMedicines() {
	    if (this.medicines == null) {

	        this.medicines = new ArrayList<Medicine>();
	        List<Checkup> allCheckups = this.getCheckups();
	        
			for (Checkup c : allCheckups){
				Hibernate.initialize(c.getMedicines());
				for(Medicine m : c.getMedicines()){
					this.medicines.add(m);
				}
	        }
	    }
	    return this.medicines;
	}
	
	@Transient
	private List<Diet> diets;	
	
	public List<Diet> getDiets() {
	    if (this.diets == null) {

	        this.diets = new ArrayList<Diet>();
	        List<Checkup> allCheckups = this.getCheckups();
	        
			for (Checkup c : allCheckups){
				for(Diet d : c.getDiets()){
					this.diets.add(d);
				}
	        }
	    }
	    return this.diets;
	}
	
	@Transient
	private List<Disease> diseases;	
	
	public List<Disease> getDiseases() {
	    if (this.diseases == null) {

	        this.diseases = new ArrayList<Disease>();
	        this.alergyDiseases = new ArrayList<Disease>();
	        this.nonAlergyDiseases = new ArrayList<Disease>();
	        List<Checkup> allCheckups = this.getCheckups();
	        
			for (Checkup c : allCheckups){
				for(Disease d : c.getDiseases()){
					this.diseases.add(d);
					if(d.getIsAllergy()==0){
						nonAlergyDiseases.add(d);
					}else{
						alergyDiseases.add(d);
					}
				}
	        }
	    }
	    return this.diseases;
	}
	
	
	@Transient
	private List<Disease> alergyDiseases;
	@Transient
	private List<Disease> nonAlergyDiseases;
	
	public List<Disease> getDiseases(int alergy){
		getDiseases();
		if(alergy == 1){
			return alergyDiseases;
		}else{
			return nonAlergyDiseases;
		}
	}
	
	@Transient
	private List<Result_Checkup> resultCheckups;	
	
	public List<Result_Checkup> getResults() {
	    if (this.resultCheckups == null) {

	        this.resultCheckups = new ArrayList<Result_Checkup>();
	        List<Checkup> allCheckups = this.getCheckups();
	        
			for (Checkup c : allCheckups){
				for(Result_Checkup r : c.getResultCheckups()){
					this.resultCheckups.add(r);
				}
	        }
	    }
	    return this.resultCheckups;
	}
	
	public PatientData() {
	}		
	
	public DoctorData getDoctor() {
		return this.doctor;
	}

	public void setDoctor(DoctorData doctor) {
		this.doctor = doctor;
	}
	
	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public List<Checkup> getCheckups() {
		return this.checkups;
	}

	public void setCheckups(List<Checkup> checkups) {
		this.checkups = checkups;
	}
	
	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setPatient(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setPatient(null);

		return appointment;
	}
	
	public List<PatientData> getPatients() {
		return this.patients;
	}

	public void setPatients(List<PatientData> patients) {
		this.patients = patients;
	}
	
	public PatientData addPatient(PatientData patient) {
		getPatients().add(patient);
		patient.setCaretaker(patient);

		return patient;
	}

	public PatientData removePatient(PatientData patient) {
		getPatients().remove(patient);
		patient.setCaretaker(null);

		return patient;
	}

	public PatientData getCaretaker() {
		return caretaker;
	}

	public void setCaretaker(PatientData caretaker) {
		this.caretaker = caretaker;
	}

	public PO_Box getPo_box() {
		return po_box;
	}

	public void setPo_box(PO_Box po_box) {
		this.po_box = po_box;
	}

	public int getPo_number() {
		return po_number;
	}

	public void setPo_number(int po_number) {
		this.po_number = po_number;
	}

	public DoctorData getDentist() {
		return dentist;
	}

	public void setDentist(DoctorData dentist) {
		this.dentist = dentist;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}