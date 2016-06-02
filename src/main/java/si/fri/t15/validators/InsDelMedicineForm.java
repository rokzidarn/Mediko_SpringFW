package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsDelMedicineForm {
	@NotNull
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
