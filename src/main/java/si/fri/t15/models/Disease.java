package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="Disease.findAll", query="SELECT d FROM Disease d")
public class Disease implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDisease", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	public Disease() {
	}	
	
	public String getDiseaseId() {
		return this.id;
	}

	public void setDiseaseId(String id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(mappedBy="diseases")
	private List<Checkup> checkups;
	
	@ManyToMany(mappedBy="diseases")
	private List<Medicine> medicines;
}
