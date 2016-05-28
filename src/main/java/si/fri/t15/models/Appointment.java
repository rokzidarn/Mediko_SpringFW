package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@NamedQueries({
	@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a"),
	@NamedQuery(name="Appointment.findAppointment", query="SELECT a FROM Appointment a WHERE a.id=?1")
})
public class Appointment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idAppointment", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Date", nullable=false)
	private Date date;
	
	@Column(nullable=false)
	private Timestamp dateTime;
	
	@ManyToOne
	@JoinColumn(name="Patient_idPatient")
	private PatientData patient;
	
	@ManyToOne
	@JoinColumn(name="Doctor_idDoctor")
	private DoctorData doctor;
	
	@ManyToOne
	private WorkDay workDay;
	
	public Appointment() {
	}

	public int getIdAppointment() {
		return this.id;
	}

	public void setIdAppointment(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@JsonIgnore
	public DoctorData getDoctor() {
		return this.doctor;
	}

	public void setDoctor(DoctorData doctor) {
		this.doctor = doctor;
	}
	
	@JsonIgnore
	public PatientData getPatient() {
		return this.patient;
	}

	public void setPatient(PatientData user) {
		this.patient = user;
	}
}