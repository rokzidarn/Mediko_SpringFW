package si.fri.t15.validators;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InsAddMedicineForm {
	@NotNull
	@Min(1)
    private int medicine;
	
	@NotNull
    private String instruction_medicine;

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(int medicine) {
		this.medicine = medicine;
	}

	public String getInstruction_medicine() {
		return instruction_medicine;
	}

	public void setInstruction_medicine(String instruction_medicine) {
		this.instruction_medicine = instruction_medicine;
	}
	
}
