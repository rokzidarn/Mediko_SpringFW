package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsertReasonForm {

	@NotNull
    private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
