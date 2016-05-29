package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class MedDelDiseaseForm {
	@NotNull
	private String disease;
	
	@NotNull
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
