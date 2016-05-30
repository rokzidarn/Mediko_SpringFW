package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.NurseData;

@Entity
@NamedQuery(name="Medical_Center.findAll", query="SELECT m FROM Medical_Center m")
public class Medical_Center implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idMedical_Center", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false, updatable=true)
	private String name;
	
	@OneToMany(mappedBy="medical_center")
	private List<DoctorData> doctors;
	
	@OneToOne
	private PO_Box po_box;
	
	@OneToMany(mappedBy="medical_center")
	private List<NurseData> nurses;
	
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
	
	public List<DoctorData> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(List<DoctorData> doctors) {
		this.doctors = doctors;
	}
	
	public List<NurseData> getNurses() {
		return this.nurses;
	}

	public void setNurses(List<NurseData> nurses) {
		this.nurses = nurses;
	}

	public PO_Box getPo_box() {
		return po_box;
	}

	public void setPo_box(PO_Box po_box) {
		this.po_box = po_box;
	}
	
}