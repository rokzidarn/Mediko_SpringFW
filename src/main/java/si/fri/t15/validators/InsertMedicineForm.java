package si.fri.t15.validators;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InsertMedicineForm {
	@NotNull
	@Min(1)
    private int imedicine;

	public int getImedicine() {
		return imedicine;
	}

	public void setImedicine(int imedicine) {
		this.imedicine = imedicine;
	}
}
