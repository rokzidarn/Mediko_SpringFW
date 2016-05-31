package si.fri.t15.models;

import java.sql.Time;
import java.util.List;

import javax.persistence.*;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

import si.fri.t15.models.user.DoctorData;

=======
>>>>>>> develop
@Entity
@NamedQuery(name="WorkDay.getWorkdayById",query="SELECT wd FROM WorkDay wd WHERE wd.id = :id")
public class WorkDay {
	
	@Id
	@Column(nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
    private Time start;
    
    @Column
    private Time end;
    
    @Column
    private int minuteInterval;
    
    @Column
    private String note;
    
    @OneToMany(mappedBy="workDay")
    private List<Appointment> appointments;
    
    @ManyToOne
	private WorkWeek workWeek;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public int getMinuteInterval() {
		return minuteInterval;
	}

	public void setMinuteInterval(int minuteInterval) {
		this.minuteInterval = minuteInterval;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@JsonIgnore
	public WorkWeek getWorkWeek() {
		return workWeek;
	}

	public void setWorkWeek(WorkWeek workWeek) {
		this.workWeek = workWeek;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    
    
}
