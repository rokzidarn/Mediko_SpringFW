package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@NamedQuery(name="Doctor.findAll", query="SELECT d FROM Doctor d")
public class Doctor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDoctor", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="First_Name", length=15, nullable=false)
	private String first_name;

	@Column(name="Last_Name", length=15, nullable=false, updatable=true)
	private String last_name;

	@Column(name="Type", length=15, nullable=false, updatable=true)
	private String type;
	
	@Column(name="Specialist", length=15, nullable=false, updatable=true)
	private String specialist;
	
	@OneToMany(mappedBy="doctor")
	private List<Pacient> pacients;
	
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments;
	
	@ManyToOne
	private Medical_Center medical_center;
	
	@ManyToMany(mappedBy="checkup")
	@JoinTable(
		      name="Doctor_Nurse",
		      joinColumns=@JoinColumn(name="D_ID", referencedColumnName="idDoctor"),
		      inverseJoinColumns=@JoinColumn(name="N_ID", referencedColumnName="idNurse"))
	private List<Nurse> nurses;
	
	@OneToMany(mappedBy="doctor")
	private List<Checkup> checkups;
	
	public Doctor() {
	}	
	
	public int getDoctorId() {
		return this.id;
	}

	public void setDoctorId(int id) {
		this.id = id;
	}	
	
	public String getFirst_Name() {
		return this.first_name;
	}

	public void setFirst_Name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLast_Name() {
		return this.last_name;
	}

	public void setLast_Name(String last_name) {
		this.last_name = last_name;
	}
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSpecialist() {
		return this.specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}
	
	public Medical_Center getMedical_Center() {
		return this.medical_center;
	}

	public void setMedical_Center(Medical_Center medical_center) {
		this.medical_center = medical_center;
	}
	
	public List<Pacient> getPacients() {
		return this.pacients;
	}

	public void setPacients(List<Pacient> pacients) {
		this.pacients = pacients;
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
	
	public Pacient addPacient(Pacient pacient) {
		getPacients().add(pacient);
		pacient.setDoctor(this);

		return pacient;
	}

	public Pacient removePacient(Pacient pacient) {
		getPacients().remove(pacient);
		pacient.setDoctor(null);

		return pacient;
	}
}