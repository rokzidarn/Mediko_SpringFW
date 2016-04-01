package si.fri.t15.models;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import java.util.*;

@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Timestamp", nullable=false, updatable=true)
	private Timestamp timestamp;
	
	@OneToMany(mappedBy="user")
	private List<Pacient> pacients;
	
	public User() {
	}	
	
	public int getUserId() {
		return this.id;
	}

	public void setUserId(int id) {
		this.id = id;
	}	
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	public List<Pacient> getPacients() {
		return this.pacients;
	}

	public void setPacients(List<Pacient> pacients) {
		this.pacients = pacients;
	}
	
	public Pacient addPacient(Pacient pacient) {
		getPacients().add(pacient);
		pacient.setUser(this);

		return pacient;
	}

	public Pacient removePacient(Pacient pacient) {
		getPacients().remove(pacient);
		pacient.setUser(null);

		return pacient;
	}
}
