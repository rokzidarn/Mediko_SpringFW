package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Disease implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDisease", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@OneToMany(mappedBy="disease")
	private List<Checkup_Disease> checkup_diseases;
	
	public Disease() {
	}	
	
	public int getDiseaseId() {
		return this.id;
	}

	public void setDiseaseId(int id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Checkup_Disease> getCheckup_Diseases() {
		return this.checkup_diseases;
	}

	public void setCheckup_Diseases(List<Checkup_Disease> checkup_diseases) {
		this.checkup_diseases = checkup_diseases;
	}
}
