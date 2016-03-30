package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Checkup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Reason", length=45, nullable=false)
	private String reason;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Doctor doctor;
	
	@OneToMany(mappedBy="checkup")
	private List<Checkup_Diet> checkup_diets;
	
	@OneToMany(mappedBy="checkup")
	private List<Checkup_Disease> checkup_diseases;
	
	@OneToMany(mappedBy="checkup")
	private List<Checkup_Medicine> checkup_medicines;
	
	@OneToMany(mappedBy="checkup")
	private List<Result_Checkup> result_checkups;
	
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
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public List<Checkup_Diet> getCheckup_Diets() {
		return this.checkup_diets;
	}

	public void setCheckup_Diets(List<Checkup_Diet> checkup_diets) {
		this.checkup_diets = checkup_diets;
	}
	
	public List<Checkup_Disease> getCheckup_Diseases() {
		return this.checkup_diseases;
	}

	public void setCheckup_Diseases(List<Checkup_Disease> checkup_diseases) {
		this.checkup_diseases = checkup_diseases;
	}
	
	public List<Checkup_Medicine> getCheckup_Medicines() {
		return this.checkup_medicines;
	}

	public void setCheckup_Medicines(List<Checkup_Medicine> checkup_medicines) {
		this.checkup_medicines = checkup_medicines;
	}
	
	public List<Result_Checkup> getResult_Checkups() {
		return this.result_checkups;
	}

	public void setResult_Checkups(List<Result_Checkup> result_checkups) {
		this.result_checkups = result_checkups;
	}
	
}
