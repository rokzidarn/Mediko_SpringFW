package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class AddDietForm {
	@NotNull
    private String diname;
	
	public String getDiname() {
		return diname;
	}

	public void setDiname(String diname) {
		this.diname = diname;
	}
}
