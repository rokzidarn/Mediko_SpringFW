package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class DeleteDiseaseForm {
	@NotNull
	private String idd;

	public String getIdd() {
		return idd;
	}

	public void setIdd(String idd) {
		this.idd = idd;
	}
}
