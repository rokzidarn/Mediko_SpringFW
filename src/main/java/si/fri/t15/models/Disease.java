package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name="Disease.findAll", query="SELECT d FROM Disease d"),
	@NamedQuery(name="Disease.findDisease", query="SELECT d FROM Disease d WHERE d.id=?1")
})
public class Disease implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDisease", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@Column(name="isAllergy", nullable=false)
	private int isAllergy;
	
	@OneToMany(mappedBy="disease", fetch=FetchType.EAGER)
	private List<Instructions> instructions;

	public int getIsAllergy() {
		return isAllergy;
	}

	public void setIsAllergy(int isAllergy) {
		this.isAllergy = isAllergy;
	}
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Instructions> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instructions> instructions) {
		this.instructions = instructions;
	}
	
	
}