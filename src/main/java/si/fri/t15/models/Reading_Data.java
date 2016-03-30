package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Reading_Data implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idReading_Data", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Data", nullable=false)
	private int data;
	
	@Column(name="Unit", length=15, nullable=false)
	private String unit;
	
	@ManyToOne
	private Reading reading;
	
	@ManyToOne
	private Result_Home result_home;
	
	@ManyToOne
	private Result_Checkup result_checkup;
}
