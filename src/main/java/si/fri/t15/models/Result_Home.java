package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import si.fri.t15.models.user.PatientData;

@Entity
@NamedQuery(name="Result_Home.findAll", query="SELECT r FROM Result_Home r")
public class Result_Home implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idResult_Home", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="Text", length=225, nullable=false)
	private String text;
	
	@OneToMany(mappedBy="result_home")
	private List<Reading_Data> reading_datas;
	
	@ManyToOne
	@JoinColumn(name="Patient_idPatient")
	private PatientData patient;
	
	public Result_Home() {
	}	
	
	public int getResult_HomeId() {
		return this.id;
	}

	public void setResult_HomeId(int id) {
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
	
	public PatientData getPatient() {
		return this.patient;
	}

	public void setPatient(PatientData patient) {
		this.patient = patient;
	}
}
