package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Pacient implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idPacient", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="First_Name", length=15, nullable=false)
	private String first_name;

	@Column(name="Last_Name", length=15, nullable=false, updatable=true)
	private String last_name;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Doctor doctor;
	
	@OneToMany(mappedBy="pacient")
	private List<Appointment> appointments;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PO_Box_idPO_Box")
	private PO_Box po_box;
	
	@OneToMany(mappedBy="pacient")
	private List<Checkup> checkups;
	
	public Pacient() {
	}	
	
	public int getPacientId() {
		return this.id;
	}

	public void setPacientId(int id) {
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
	
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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
}
