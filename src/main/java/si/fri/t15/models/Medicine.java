package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
	@NamedQuery(name="Medicine.findAll", query="SELECT m FROM Medicine m"),
	@NamedQuery(name="Medicine.findMedicine", query="SELECT m FROM Medicine m WHERE m.id=?1")
})

public class Medicine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idMedicine", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@Column(name="Link", length=255, nullable=false)
	private String link;
	
	@Column(name="Type", nullable=false)
	private char type;
	
	@ManyToMany(mappedBy="medicines")
	private List<Checkup> checkups;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
    		  name="Disease_Medicine",
		      joinColumns=@JoinColumn(name="M_ID", referencedColumnName="idMedicine"),
		      inverseJoinColumns=@JoinColumn(name="D_ID", referencedColumnName="idDisease"))
	private List<Disease> diseases;
	
	@OneToMany(mappedBy="medicine")
	private List<Instructions> instructions;
	
	public Medicine() {
	}	
	
	public int getMedicineId() {
		return this.id;
	}

	public void setMedicineId(int id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public char getType() {
		return this.type;
	}

	public void setType(char type) {
		this.type = type;
	}
	
	public List<Instructions> getInstructions() {
		return this.instructions;
	}

	public void setInstructions(List<Instructions> instructions) {
		this.instructions = instructions;
	}
	
	public Instructions addInstruction(Instructions instruction) {
		getInstructions().add(instruction);
		instruction.setMedicine(this);

		return instruction;
	}

	public Instructions removeInstruction(Instructions instruction) {
		getInstructions().remove(instruction);
		instruction.setMedicine(null);

		return instruction;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}
	
}
