package si.fri.t15.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Reading implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idReading", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Type", length=45, nullable=false)
	private String type;
	
	@Column(name="Date", nullable=false)
	private Date date;
	
	@OneToMany(mappedBy="reading")
	private List<Reading_Data> reading_datas;
}
