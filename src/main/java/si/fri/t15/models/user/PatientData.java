package si.fri.t15.models.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import si.fri.t15.base.models.UserData;
import si.fri.t15.models.Appointment;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.PO_Box;

@Entity
public class PatientData extends UserData {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private PatientData caretaker;
	
	@ManyToOne
	private DoctorData doctor;
	
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointments;
	
	private int po_number;
	
	@OneToOne
	private PO_Box po_box;
	
	@OneToMany(mappedBy="patient")
	private List<Checkup> checkups;
	
	@OneToMany(mappedBy="caretaker")
	private List<PatientData> patients;
	
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
		patient.setCaretaker(this);

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

}
