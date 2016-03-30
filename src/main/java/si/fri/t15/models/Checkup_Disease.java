package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Checkup_Disease implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup_Disease", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Checkup checkup;
	
	@ManyToOne
	private Disease disease;
	
	public Checkup_Disease() {
	}

	public int getCheckup_DiseaseId() {
		return this.id;
	}

	public void setCheckup_DiseaseId(int id) {
		this.id = id;
	}	
	
	public Checkup getCheckup() {
		return this.checkup;
	}

	public void setCheckup(Checkup checkup) {
		this.checkup = checkup;
	}
	
	public Disease getDisease() {
		return this.disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}
}
