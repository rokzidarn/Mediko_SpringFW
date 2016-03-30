package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Checkup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idCheckup", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Reason", length=45, nullable=false)
	private String reason;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Doctor doctor;
}
