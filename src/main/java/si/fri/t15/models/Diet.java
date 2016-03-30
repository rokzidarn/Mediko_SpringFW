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
}
