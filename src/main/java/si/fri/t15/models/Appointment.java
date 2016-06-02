package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.fri.t15.base.models.UserData;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@NamedQueries({
	@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a"),
	@NamedQuery(name="Appointment.findAppointment", query="SELECT a FROM Appointment a WHERE a.id=?1"),
	@NamedQuery(name="Appointment.findByPatientDoctorAndDate", query="SELECT a FROM Appointment a WHERE a.patient = :patient AND a.doctor = :doctor AND a.dateTime > :date")
})
public class Appointment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idAppointment", nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Date")
	private Date date;
	
	@Column(nullable=false)
	private Timestamp dateTime;
	
	@OneToOne
	private UserData orderedBy;

	@ManyToOne
	@JoinColumn(name="Patient_idPatient")
	private PatientData patient;
	
	@ManyToOne
	@JoinColumn(name="Doctor_idDoctor")
	private DoctorData doctor;
	
	@ManyToOne
	private WorkDay workDay;
	
	transient private boolean isTaken;
	transient private boolean canRelease;
	
	public boolean isCanRelease() {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.HOUR, 12);
		return dateTime.after(new Timestamp(today.getTimeInMillis()));
	}

	public boolean isTaken() {
		return patient != null;
	}

	@Column(nullable=false)
	private boolean doctorFreeTime;
	
	
	public boolean isDoctorFreeTime() {
		return doctorFreeTime;
	}

	public void setDoctorFreeTime(boolean doctorFreeTime) {
		this.doctorFreeTime = doctorFreeTime;
	}

	public boolean isDoctor() {
		return (patient.equals(doctor));
	}

	public Appointment() {
	}

	public int getIdAppointment() {
		return this.id;
	}

	public void setIdAppointment(int id) {
		this.id = id;
	}

	public Date getDate() {
		return new Date(this.dateTime.getTime());
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
	

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	@JsonIgnore
	public WorkDay getWorkDay() {
		return workDay;
	}
	
	
	public void setWorkDay(WorkDay workDay) {
		this.workDay = workDay;
	}

	@JsonIgnore
	public UserData getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(UserData orderedBy) {
		this.orderedBy = orderedBy;
	}
	
}