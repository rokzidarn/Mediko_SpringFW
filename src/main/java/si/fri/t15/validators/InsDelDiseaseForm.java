package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsDelDiseaseForm {
	@NotNull
	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
