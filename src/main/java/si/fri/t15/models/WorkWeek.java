package si.fri.t15.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import si.fri.t15.models.user.DoctorData;

@Entity
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
    
    
}
