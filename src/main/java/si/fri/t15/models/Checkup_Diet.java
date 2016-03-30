package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Checkup_Diet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup_Diet", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@OneToMany(mappedBy="diet")
	private List<Instructions_Diet> instruction_diets;
	
	public Checkup_Diet() {
	}

	public int getCheckup_DietId() {
		return this.id;
	}

	public void setCheckup_DietId(int id) {
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
}
