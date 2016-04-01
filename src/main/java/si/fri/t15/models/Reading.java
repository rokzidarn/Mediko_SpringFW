package si.fri.t15.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQuery(name="Reading.findAll", query="SELECT r FROM Reading r")
public class Reading implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idReading", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Type", length=45, nullable=false)
	private String type;
	
	@Column(name="Date")
	private Date date;
	
	@OneToMany(mappedBy="reading")
	private List<Reading_Data> reading_datas;
	
	public Reading() {
	}	
	
	public int getReadingId() {
		return this.id;
	}

	public void setReadingId(int id) {
		this.id = id;
	}	
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Date getDate() {
		return this.date;
	}

	public void Date(Date date) {
		this.date = date;
	}
	
	public List<Reading_Data> getReading_Datas() {
		return this.reading_datas;
	}

	public void setReading_Datas(List<Reading_Data> reading_datas) {
		this.reading_datas = reading_datas;
	}
}
