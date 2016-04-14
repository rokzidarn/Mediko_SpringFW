package si.fri.t15.base.models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="First_Name", length=15, nullable=false, updatable=true)
	protected String first_name;

	@Column(name="Last_Name", length=15, nullable=false, updatable=true)
	protected String last_name;
	
	@Column(name="Birth_date",nullable=false, updatable=true)
	protected Date birth_date;
	
	@Column(name="Address", length=100, nullable=false, updatable=true)
	protected String address;
	
	@Column(name="Sex",  nullable=false, updatable=true)
	protected char sex;

	public char isSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
