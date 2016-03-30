package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Doctor_Nurse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDoctor_Nurse", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Doctor doctor;
	
	@ManyToOne
	private Nurse nurse;
	
	public Doctor_Nurse() {
	}	
	
	public int getDoctor_NurseId() {
		return this.id;
	}

	public void setDoctor_NurseId(int id) {
		this.id = id;
	}	
	
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public Nurse getNurse() {
		return this.nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}