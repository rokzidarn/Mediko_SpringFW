package si.fri.t15.validators;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InsAddDietForm {
	@NotNull
	@Min(1)
    private int diet;
	
	@NotNull
    private String instruction_diet;

	public int getDiet() {
		return diet;
	}

	public void setDiet(int diet) {
		this.diet = diet;
	}

	public String getInstruction_diet() {
		return instruction_diet;
	}

	public void setInstruction_diet(String instruction_diet) {
		this.instruction_diet = instruction_diet;
	}
	
}
