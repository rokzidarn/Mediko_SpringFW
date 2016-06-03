package si.fri.t15.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import si.fri.t15.models.user.DoctorData;

@Entity
@NamedQueries({
	@NamedQuery(name="WorkWeek.getWorkWeekByStartDateAndDoctor", query="SELECT ww FROM WorkWeek ww WHERE ww.startDate = :startDate and ww.doctor = :doctor"),
	@NamedQuery(name="WorkWeek.getWorkWeekById", query="SELECT ww FROM WorkWeek ww WHERE ww.id = :id")
})
public class WorkWeek {

	@Id
	@Column(nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
    private Date startDate;

    @OneToMany(mappedBy="workWeek")
    private List<WorkDay> workDays; // workDays.get(0) je delovni dan na startDate
    
    @ManyToOne
	private DoctorData doctor;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<WorkDay> getWorkDays() {
		return workDays;
	}

	public void setWorkDays(List<WorkDay> workDays) {
		this.workDays = workDays;
	}

	public DoctorData getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorData doctor) {
		this.doctor = doctor;
	}
    
    
    
    
}
