package si.fri.t15.validators;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InsertDietForm {
	@NotNull
	@Min(1)
    private int idiet;

	public int getIdiet() {
		return idiet;
	}

	public void setIdiet(int idiet) {
		this.idiet = idiet;
	}
}
