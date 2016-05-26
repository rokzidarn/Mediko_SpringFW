package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
	@NamedQuery(name="Instructions.findAll", query="SELECT i FROM Instructions i"),
	@NamedQuery(name="Instructions.findInstructions", query="SELECT i FROM Instructions i WHERE i.id=?1")
})

public class Instructions implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idInstuctions", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@Column(name="Duration", nullable=false)
	private int duration;
	
	@Column(name="Text", length=225, nullable=false)
	private String text;
	
	
	@ManyToOne
	private Medicine medicine;
	
	@ManyToOne
	private Disease disease;
	
	@ManyToOne
	private Diet diet;
	
	public Instructions() {
	}	
	
	public int getInstructionsId() {
		return this.id;
	}

	public void setInstructionsId(int id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@JsonIgnore
	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	
	@JsonIgnore
	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	@JsonIgnore
	public Diet getDiet() {
		return diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}
	
}
