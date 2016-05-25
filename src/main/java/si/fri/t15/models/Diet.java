package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name="Diet.findAll", query="SELECT d FROM Diet d"),
	@NamedQuery(name="Diet.findDiet", query="SELECT d FROM Diet d WHERE d.id=?1")
})
public class Diet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDiet", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@OneToMany(mappedBy="diet" , fetch=FetchType.EAGER)
	private List<Instructions_Diet> instruction_diets;
	
	@OneToMany(mappedBy="diet")
	private List<Instructions> instructions;
	
	@ManyToMany(mappedBy="diets")
	private List<Checkup> checkups;
	
	public Diet() {
	}	
	
	public int getDietId() {
		return this.id;
	}

	public void setDietId(int id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Instructions_Diet> getInstructions_Diets() {
		return this.instruction_diets;
	}

	public void setInstructions_Diets(List<Instructions_Diet> instruction_diets) {
		this.instruction_diets = instruction_diets;
	}

	public List<Instructions> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instructions> instructions) {
		this.instructions = instructions;
	}
	
	
}
