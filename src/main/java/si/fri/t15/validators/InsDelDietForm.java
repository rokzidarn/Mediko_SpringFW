package si.fri.t15.validators;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InsDelDietForm {
	@NotNull
	@Min(1)
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
