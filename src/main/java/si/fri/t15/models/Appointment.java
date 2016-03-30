package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class Appointment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idAppointment", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Date", nullable=false)
	private Date date;
	
	@ManyToOne
	private Pacient pacient;
	
	@ManyToOne
	private Doctor doctor;
}