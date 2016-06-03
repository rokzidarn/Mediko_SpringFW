package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class AddMedicineForm {
	@NotNull
    private String mname;

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}
}
