package si.fri.t15.validators;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

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
