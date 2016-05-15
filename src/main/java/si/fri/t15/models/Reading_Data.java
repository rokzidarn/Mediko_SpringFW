package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name="Reading_Data.findAll", query="SELECT r FROM Reading_Data r"),
	@NamedQuery(name="Reading_Data.findByCheckup", query="SELECT r FROM Reading_Data r WHERE r.result_checkup.id=?1"),
})
public class Reading_Data implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idReading_Data", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Data", nullable=false)
	private String data;
	
	@Column(name="Unit", length=15, nullable=false)
	private String unit;
	
	@ManyToOne
	@JoinColumn(name="Reading_idReading")
	private Reading reading;

	@ManyToOne
	@JoinColumn(name="Result_Checkup_idResult_Checkup")
	private Result_Checkup result_checkup;

	@ManyToOne
	@JoinColumn(name="Result_Home_idResult_Home")
	private Result_Home result_home;
	
	public Reading_Data() {
	}	
	
	public int getReading_DataId() {
		return this.id;
	}

	public void setReading_DataId(int id) {
		this.id = id;
	}	
	
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Reading getReading() {
		return this.reading;
	}

	public void setReading(Reading reading) {
		this.reading = reading;
	}
	
	public Result_Home getResult_Home() {
		return this.result_home;
	}

	public void seResult_Home(Result_Home result_home) {
		this.result_home = result_home;
	}
	
	public Result_Checkup getResult_Checkup() {
		return this.result_checkup;
	}

	public void setResult_Checkup(Result_Checkup result_checkup) {
		this.result_checkup = result_checkup;
	}
}
