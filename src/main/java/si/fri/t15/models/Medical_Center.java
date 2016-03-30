package si.fri.t15.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Medical_Center implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idMedical_Center", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name", length=45, nullable=false, updatable=true)
	private String name;
	
	@OneToMany(mappedBy="medical_center")
	private List<Doctor> doctors;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PO_Box_idPO_Box")
	private PO_Box po_box;
	
	@OneToMany(mappedBy="medial_center")
	private List<Nurse> nurses;
}