package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="Result_Checkup.findAll", query="SELECT r FROM Result_Checkup r")
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
	
	@ManyToOne
	@JoinColumn(name="Checkup_idCheckup")
	private Checkup checkup;
	
	public Result_Checkup() {
	}	
	
	public int getResult_CheckupId() {
		return this.id;
	}

	public void setResult_CheckupId(int id) {
		this.id = id;
	}	
	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public List<Reading_Data> getReading_Datas() {
		return this.reading_datas;
	}

	public void setReading_Datas(List<Reading_Data> reading_datas) {
		this.reading_datas = reading_datas;
	}
	
	public Checkup getCheckup() {
		return this.checkup;
	}

	public void setCheckup(Checkup checkup) {
		this.checkup = checkup;
	}
}
