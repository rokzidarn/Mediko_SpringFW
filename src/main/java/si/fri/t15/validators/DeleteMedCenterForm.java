package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class DeleteMedCenterForm {
	@NotNull
	private int idmc;

	public int getIdmc() {
		return idmc;
	}

	public void setIdmc(int idmc) {
		this.idmc = idmc;
	}
}
