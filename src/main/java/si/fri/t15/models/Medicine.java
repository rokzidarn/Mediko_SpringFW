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
}
