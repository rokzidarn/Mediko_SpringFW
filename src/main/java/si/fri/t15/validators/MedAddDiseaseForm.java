package si.fri.t15.validators;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MedAddDiseaseForm {
	@NotNull
	private String disease;
	
	@NotNull
	@Min(1)
	private int medicine;

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(int medicine) {
		this.medicine = medicine;
	}
}
