package si.fri.t15.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idUser", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Username", length=15, nullable=false)
	private String username;

	@Column(name="Email", length=45, nullable=false)
	private String email;

	@Column(name="Password", length=15, nullable=false, updatable=true)
	private String password;
	
	@Column(name="Status", nullable=false, updatable=true)
	private char status;
}
