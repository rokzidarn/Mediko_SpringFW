package si.fri.t15.validators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class InsertDiseaseForm {
	@NotNull
	@Pattern(regexp="/^[0-9a-zA-Z-_.]{1,12}$/")
    private String idisease;

	public String getIdisease() {
		return idisease;
	}

	public void setIdisease(String idisease) {
		this.idisease = idisease;
	}	
}
