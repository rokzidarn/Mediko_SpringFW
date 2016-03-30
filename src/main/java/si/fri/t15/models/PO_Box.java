package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class PO_Box implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idPO_Box", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="City", length=45, nullable=false)
	private String city;
}