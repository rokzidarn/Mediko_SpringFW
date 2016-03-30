package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Doctor_Nurse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDoctor_Nurse", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
}