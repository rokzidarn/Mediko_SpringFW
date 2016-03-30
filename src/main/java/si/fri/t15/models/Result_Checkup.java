package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Result_Checkup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Result_Checkup", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Text", length=225, nullable=false)
	private String text;
	
	@OneToMany(mappedBy="result_checkup")
	private List<Reading_Data> reading_datas;
}
