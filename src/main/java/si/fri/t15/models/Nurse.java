package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Nurse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idNurse", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="First_Name", length=15, nullable=false)
	private String first_name;

	@Column(name="Last_Name", length=15, nullable=false, updatable=true)
	private String last_name;
	
	@ManyToOne
	private Medical_Center medical_center;
	
	@ManyToMany(mappedBy="nurses")
	private List<Doctor> doctors;
	
	public Nurse() {
	}	
	
	public int getNurseId() {
		return this.id;
	}

	public void setNurseId(int id) {
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
	
	public Medical_Center getMedical_Center() {
		return this.medical_center;
	}

	public void setMedical_Center(Medical_Center medical_center) {
		this.medical_center = medical_center;
	}
}