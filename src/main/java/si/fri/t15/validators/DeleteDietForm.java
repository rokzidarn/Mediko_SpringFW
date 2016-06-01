package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class DeleteDietForm {
	@NotNull
    private int iddi;

	public int getIddi() {
		return iddi;
	}

	public void setIddi(int iddi) {
		this.iddi = iddi;
	}
}
