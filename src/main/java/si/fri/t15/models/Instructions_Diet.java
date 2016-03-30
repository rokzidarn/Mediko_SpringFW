package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Instructions_Diet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idInstuctions_Diet", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false)
	private String name;
	
	@Column(name="Duration", nullable=false)
	private int duration;
	
	@Column(name="Text", length=225, nullable=false)
	private String text;
	
	@ManyToOne
	private Diet diet;
	
	public Instructions_Diet() {
	}	
	
	public int getInstructions_DietId() {
		return this.id;
	}

	public void setInstructions_DietId(int id) {
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
	
	public Diet getDiet() {
		return this.diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}
}
