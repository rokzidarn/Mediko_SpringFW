package si.fri.t15.models.user;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

import si.fri.t15.base.models.UserData;

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

	@Column(name="Timestamp", nullable=false, updatable=true)
	private Timestamp lastLogin;
	
	@Column(name="PasswordResetToken", length=15, updatable=true, unique=true)
	private String passwordResetToken;
	
	@OneToOne
	private UserData data;
	
	public User() {
	}	
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
	
	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public UserData getData() {
		return data;
	}

	public void setData(PatientData data) {
		this.data = data;
	}
	
	public PatientData getPatientData() {
		return (PatientData) data;
	}

	public void setPatientData(PatientData data) {
		this.data = data;
	}
	
	public DoctorData getDoctorData() {
		return (DoctorData) data;
	}

	public void setDoctorData(DoctorData data) {
		this.data = data;
	}
	
	public NurseData getNurseData() {
		return (NurseData) data;
	}

	public void setNurseData(NurseData data) {
		this.data = data;
	}
}
