package si.fri.t15.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="UserRole.findByRole", query="SELECT r FROM UserRole r WHERE r.role = :role")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Role {
		ROLE_USER, ROLE_NURSE, ROLE_DOCTOR, ROLE_ADMIN;
	}

	@Id
	@Column(name = "role", length = 45, nullable = false)
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Role other = (Role) obj;
		if (role == null) {
			if (other != null) {
				return false;
			}
		} else if (!this.role.equals(other.name())) {
			return false;
		}
		return true;
	}
}