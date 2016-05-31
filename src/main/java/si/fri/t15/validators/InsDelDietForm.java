package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsDelDietForm {
	@NotNull
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
