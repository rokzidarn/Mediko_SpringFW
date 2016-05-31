package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class SelectDoctorForm {

	@NotNull
	private int doctor;
	
	public int getDoctor() {
		return doctor;
	}

	public void setDoctor(int doctor) {
		this.doctor = doctor;
	}

	public int getDentist() {
		return dentist;
	}

	public void setDentist(int dentist) {
		this.dentist = dentist;
	}

	@NotNull
	private int dentist;

}
