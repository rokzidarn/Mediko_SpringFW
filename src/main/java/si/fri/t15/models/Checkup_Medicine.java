package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Checkup_Medicine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup_Medicine", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Checkup checkup;
	
	@ManyToOne
	private Medicine medicine;
	
	public Checkup_Medicine() {
	}

	public int getCheckup_MedicineId() {
		return this.id;
	}

	public void setCheckup_MedicineId(int id) {
		this.id = id;
	}	
	
	public Checkup getCheckup() {
		return this.checkup;
	}

	public void setCheckup(Checkup checkup) {
		this.checkup = checkup;
	}
	
	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
}
