package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Disease_Medicine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idDisease_Medicine", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Medicine medicine;
	
	@ManyToOne
	private Disease disease;
}
