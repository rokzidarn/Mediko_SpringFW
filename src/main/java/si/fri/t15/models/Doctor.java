package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
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
	
	@OneToMany(mappedBy="doctor")
	private List<Doctor_Nurse> doctor_nurses;
	
	@OneToMany(mappedBy="doctor")
	private List<Checkup> checkups;
}