package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Medical_Center implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idMedical_Center", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false, updatable=true)
	private String name;
}