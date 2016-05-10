package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;

@Entity
@NamedQueries({
	@NamedQuery(name="Checkup.findAll", query="SELECT c FROM Checkup c"),
	@NamedQuery(name="Checkup.findCheckup", query="SELECT c FROM Checkup c WHERE c.id=?1")
})
public class Checkup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Reason", length=45, nullable=false)
	private String reason;
	
	@OneToOne
	private Appointment appointment;
	

	@ManyToOne
	@JoinColumn(name="Patient_idPatient")
	private PatientData patient;
	
	@ManyToOne
	@JoinColumn(name="Doctor_idDoctor")
	private DoctorData doctor;
	
	@ManyToMany
	@JoinTable(
		      name="Checkup_Disease",
		      joinColumns=@JoinColumn(name="M_ID", referencedColumnName="idCheckup"),
		      inverseJoinColumns=@JoinColumn(name="D_ID", referencedColumnName="idDisease"))
	private List<Disease> diseases;

	@ManyToMany
	@JoinTable(
		      name="Checkup_Medicine",
		      joinColumns=@JoinColumn(name="C_ID", referencedColumnName="idCheckup"),
		      inverseJoinColumns=@JoinColumn(name="M_ID", referencedColumnName="idMedicine"))
	private List<Medicine> medicines;
	
	@ManyToMany
	@JoinTable(
		      name="Checkup_Diet",
		      joinColumns=@JoinColumn(name="M_ID", referencedColumnName="idCheckup"),
		      inverseJoinColumns=@JoinColumn(name="D_ID", referencedColumnName="idDiet"))
	private List<Diet> diets;
	
	@OneToMany(mappedBy="checkup")
	private List<Result_Checkup> resultCheckups;
	
	public Checkup() {
	}	
	
	public int getCheckupId() {
		return this.id;
	}

	public void setCheckupId(int id) {
		this.id = id;
	}	
	
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
	public PatientData getPatient() {
		return this.patient;
	}

	public void setPatient(PatientData patient) {
		this.patient = patient;
	}

	public void setDoctor(DoctorData doctor) {
		this.doctor = doctor;
	}
	
	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public List<Diet> getDiets() {
		return diets;
	}

	public void setDiets(List<Diet> diets) {
		this.diets = diets;
	}

	public List<Result_Checkup> getResultCheckups() {
		return resultCheckups;
	}

	public void setResultCheckups(List<Result_Checkup> resultCheckups) {
		this.resultCheckups = resultCheckups;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DoctorData getDoctor() {
		return doctor;
	}
}