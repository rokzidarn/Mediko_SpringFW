package si.fri.t15.validators;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddMedCenterForm {
	@NotNull
    private String mcname;
	
	@NotNull
	@Min(1000)
	@Max(9999)
    private int pid;

	public String getMcname() {
		return mcname;
	}

	public void setMcname(String mcname) {
		this.mcname = mcname;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}
