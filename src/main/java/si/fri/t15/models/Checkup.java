package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="Checkup.findAll", query="SELECT c FROM Checkup c")
public class Checkup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Reason", length=45, nullable=false)
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="Patient_idPatient")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name="Doctor_idDoctor")
	private Doctor doctor;
	
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
	
	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
