package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
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
	
	@OneToMany(mappedBy="medicine")
	private List<Disease_Medicine> disease_medicines;
	
	@OneToMany(mappedBy="medicine")
	private List<Instructions> instructions;
	
	@OneToMany(mappedBy="medicine")
	private List<Checkup_Medicine> checkup_medicines;
	
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
	
	public List<Disease_Medicine> getDisease_Medicines() {
		return this.disease_medicines;
	}

	public void setDisease_Medicines(List<Disease_Medicine> disease_medicines) {
		this.disease_medicines = disease_medicines;
	}
	
	public List<Instructions> getInstructions() {
		return this.instructions;
	}

	public void setInstructions(List<Instructions> instructions) {
		this.instructions = instructions;
	}
	
	public List<Checkup_Medicine> getCheckup_Medicines() {
		return this.checkup_medicines;
	}

	public void setCheckup_Medicines(List<Checkup_Medicine> checkup_medicines) {
		this.checkup_medicines = checkup_medicines;
	}
}
