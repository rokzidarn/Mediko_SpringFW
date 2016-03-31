package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="Medicine.findAll", query="SELECT m FROM Medicine m")
public class Medicine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idMedicine", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@Column(name="Type", nullable=false)
	private char type;
	
	@ManyToMany(mappedBy="medicine")
	@JoinTable(
    		  name="Checkup_Medicine",
		      joinColumns=@JoinColumn(name="M_ID", referencedColumnName="idMedicine"),
		      inverseJoinColumns=@JoinColumn(name="C_ID", referencedColumnName="idCheckup"))
	private List<Checkup> checkups;
	
	@ManyToMany(mappedBy="medicine")
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
}
