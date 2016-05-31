package si.fri.t15.validators;

import javax.validation.constraints.NotNull;

public class InsertResultForm {
	@NotNull
	//@Pattern(regexp="/^(Krvni tlak|Glukoza)$/")
    private String itype;
	
	@NotNull
    private String iresult;
	
	@NotNull
    private String itext;

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public String getIresult() {
		return iresult;
	}

	public void setIresult(String iresult) {
		this.iresult = iresult;
	}

	public String getItext() {
		return itext;
	}

	public void setItext(String itext) {
		this.itext = itext;
	}
}
