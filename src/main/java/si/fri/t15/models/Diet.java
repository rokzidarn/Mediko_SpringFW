package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Diet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDiet", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@OneToMany(mappedBy="diet")
	private List<Instructions_Diet> instruction_diets;
	
	@OneToMany(mappedBy="diet")
	private List<Checkup_Diet> checkup_diets;
	
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
	
	public List<Checkup_Diet> getCheckup_Diets() {
		return this.checkup_diets;
	}

	public void setCheckup_Diets(List<Checkup_Diet> checkup_diets) {
		this.checkup_diets = checkup_diets;
	}
}
