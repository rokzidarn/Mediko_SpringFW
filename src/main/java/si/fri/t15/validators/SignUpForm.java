package si.fri.t15.validators;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpForm {
	
	@NotNull
	@Pattern(regexp="/^[0-9a-zA-Z-_]+@[0-9a-zA-Z-_]+[.][0-9a-zA-Z-_]{2,4}$/")
    private String email;
	
	@NotNull
    @Size(min=2, max=16)
	@Pattern(regexp="/^[0-9a-zA-Z-_]{2,16}$/")
    private String username;

    @NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String password;
    
    @NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String repeatpassword;
    
    @NotNull
    @Size(min=2, max=16)
	@Pattern(regexp="/^[a-zA-Z ]{2,16}$/")
    private String first_name;
	
	@NotNull
	@Size(min=2, max=16)
	@Pattern(regexp="/^[a-zA-Z ]{2,24}$/")
    private String last_name;

	@NotNull
	@Min(1000)
	@Max(9000)
    private int po_box;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getRepeatpassword() {
		return repeatpassword;
	}

	public void setRepeatpassword(String repeatpassword) {
		this.repeatpassword = repeatpassword;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getPo_box() {
		return po_box;
	}

	public void setPo_box(int po_box) {
		this.po_box = po_box;
	}
}
