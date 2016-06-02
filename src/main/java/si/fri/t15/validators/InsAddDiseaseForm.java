package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsAddDiseaseForm {	
	@NotNull
    private String disease;
	
	@NotNull
    private String instruction;

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
