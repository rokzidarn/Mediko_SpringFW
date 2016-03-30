package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Medical_Center implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idMedical_Center", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false, updatable=true)
	private String name;
	
	@OneToMany(mappedBy="medical_center")
	private List<Doctor> doctors;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PO_Box_idPO_Box")
	private PO_Box po_box;
	
	@OneToMany(mappedBy="medial_center")
	private List<Nurse> nurses;
	
	public Medical_Center() {
	}	
	
	public int getMedical_CenterId() {
		return this.id;
	}

	public void setMedical_CenterId(int id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	public List<Nurse> getNursees() {
		return this.nurses;
	}

	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}
}