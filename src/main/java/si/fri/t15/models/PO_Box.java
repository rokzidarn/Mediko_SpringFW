package si.fri.t15.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name="PO_Box.findAll", query="SELECT p FROM PO_Box p"),
	@NamedQuery(name="PO_Box.findPOBox", query="SELECT p FROM PO_Box p WHERE p.id=?1")
})

public class PO_Box implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idPO_Box", length=4, nullable=false, updatable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="City", length=45, nullable=false)
	private String city;

	public PO_Box() {
	}	
	
	public int getPO_BoxId() {
		return this.id;
	}

	public void setPO_BoxId(int id) {
		this.id = id;
	}	
	
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}