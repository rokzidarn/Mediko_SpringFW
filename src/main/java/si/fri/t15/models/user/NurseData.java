package si.fri.t15.models.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import si.fri.t15.base.models.UserData;
import si.fri.t15.models.Medical_Center;

@Entity
public class NurseData extends UserData {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Medical_Center medical_center;
	
	@ManyToMany(mappedBy="nurses")
	private List<DoctorData> doctors;
	
	public NurseData() {
	}

	public Medical_Center getMedical_center() {
		return medical_center;
	}

	public void setMedical_center(Medical_Center medical_center) {
		this.medical_center = medical_center;
	}

	public List<DoctorData> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorData> doctors) {
		this.doctors = doctors;
	}
	
	public DoctorData addDoctor(DoctorData doctor) {
		getDoctors().add(doctor);
		doctor.addNurse(this);

		return doctor;
	}

	public DoctorData removeDoctor(DoctorData doctor) {
		getDoctors().remove(doctor);
		doctor.removeNurse(this);

		return doctor;
	}
}
