package si.fri.t15.models.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import si.fri.t15.base.models.UserData;

@Entity
public class EmergencyContactData extends UserData {

	private static final long serialVersionUID = 1L;

	public enum Relationship {
		SIBLING, PARENT, GRANDPARENT, FOSTER_PARENT, SIGNIFICANT_OTHER, CARETAKER
	}

	@Column(name = "Address", length = 100, nullable = false, updatable = true)
	private String address;
	
	@Column
	private String relationshipType;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

}
