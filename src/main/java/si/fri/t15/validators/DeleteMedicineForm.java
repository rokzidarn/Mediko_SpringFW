package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class DeleteMedicineForm {
	@NotNull
	private int idm;

	public int getIdm() {
		return idm;
	}

	public void setIdm(int idm) {
		this.idm = idm;
	}
}
