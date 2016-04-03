package si.fri.t15.models.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import si.fri.t15.base.models.UserData;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.Medical_Center;

@Entity
public class DoctorData extends UserData {

	private static final long serialVersionUID = 1L;

	@Column(name="Type", length=15, nullable=false, updatable=true)
	private String type;
	
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
	
	public List<PatientData> getPatients() {
		return this.patients;
	}

	public void setPatients(List<PatientData> patients) {
		this.patients = patients;
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
	
	public Medical_Center getMedical_center() {
		return medical_center;
	}

	public void setMedical_center(Medical_Center medical_center) {
		this.medical_center = medical_center;
	}

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
