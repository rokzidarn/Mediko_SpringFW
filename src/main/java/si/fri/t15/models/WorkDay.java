package si.fri.t15.models;

import java.sql.Time;
import java.util.List;

import javax.persistence.*;

@Entity
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
    
    @OneToMany(mappedBy="workDay")
    private List<Appointment> appointments;
    
    @ManyToOne
	private WorkWeek workWeek;
}
