package si.fri.t15.models.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.fri.t15.base.models.UserData;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Medical_Center;

@Entity
@NamedQueries({
	@NamedQuery(name="DoctorData.GetAvailableDoctors", query="SELECT d FROM DoctorData d WHERE d.type=:type AND (d.id = :users OR maxPatients > (SELECT count(p) FROM PatientData p WHERE p.doctor = d OR p.dentist = d))"),
	@NamedQuery(name="DoctorData.GetDoctorById", query="SELECT d FROM DoctorData d WHERE d.id=:id"),
	@NamedQuery(name="DoctorData.GetAllDoctors", query="SELECT d FROM DoctorData d")
})
public class DoctorData extends UserData {

	public int getMaxPatients() {
		return maxPatients;
	}

	public void setMaxPatients(int maxPatients) {
		this.maxPatients = maxPatients;
	}

	private static final long serialVersionUID = 1L;

	@Column(name="Type", length=15, nullable=true, updatable=true)
	private String type;
	
	@Column(nullable=false, updatable=true)
	private int maxPatients;
	
	@OneToMany(mappedBy="doctor")
	private List<PatientData> patients;
	
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments;
	
	@ManyToOne
	private Medical_Center medical_center;
	
	@ManyToMany
	@JoinTable(
		      name="Doctor_Nurse",
		      joinColumns=@JoinColumn(name="D_ID", referencedColumnName="id"),
		      inverseJoinColumns=@JoinColumn(name="N_ID", referencedColumnName="id"))
	private List<NurseData> nurses;
	
	@OneToMany(mappedBy="doctor")
	private List<Checkup> checkups;
	
	public DoctorData() {
	}	
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonIgnore 
	public List<PatientData> getPatients() {
		return this.patients;
	}

	public void setPatients(List<PatientData> patients) {
		this.patients = patients;
	}
	
	@JsonIgnore 
	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	@JsonIgnore 
	public List<Checkup> getCheckups() {
		return this.checkups;
	}

	public void setCheckups(List<Checkup> checkups) {
		this.checkups = checkups;
	}
	
	public Checkup addCheckup(Checkup checkup) {
		getCheckups().add(checkup);
		checkup.setDoctor(this);

		return checkup;
	}

	public Checkup removeCheckup(Checkup checkup) {
		getCheckups().remove(checkup);
		checkup.setDoctor(null);

		return checkup;
	}
	
	@JsonIgnore 
	public Medical_Center getMedical_center() {
		return medical_center;
	}

	public void setMedical_center(Medical_Center medical_center) {
		this.medical_center = medical_center;
	}

	@JsonIgnore 
	public List<NurseData> getNurses() {
		return nurses;
	}

	public void setNurses(List<NurseData> nurses) {
		this.nurses = nurses;
	}

	public PatientData addPatient(PatientData patient) {
		getPatients().add(patient);
		patient.setDoctor(null);
		
		return patient;
	}

	public PatientData removePatient(PatientData patient) {
		getPatients().remove(patient);
		patient.setDoctor(null);

		return patient;
	}
	
	public NurseData addNurse(NurseData nurse) {
		getNurses().add(nurse);
		nurse.addDoctor(this);

		return nurse;
	}

	public NurseData removeNurse(NurseData nurse) {
		getNurses().remove(nurse);
		nurse.removeDoctor(this);

		return nurse;
	}
	
}
