package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Doctor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDoctor", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="First_Name", length=15, nullable=false)
	private String first_name;

	@Column(name="Last_Name", length=15, nullable=false, updatable=true)
	private String last_name;

	@Column(name="Type", length=15, nullable=false, updatable=true)
	private String type;
	
	@Column(name="Specialist", length=15, nullable=false, updatable=true)
	private String specialist;
}