package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsertDiagnosisForm {
	@NotNull
    private String idiagnosis;

	public String getIdiagnosis() {
		return idiagnosis;
	}

	public void setIdiagnosis(String idiagnosis) {
		this.idiagnosis = idiagnosis;
	}
}
