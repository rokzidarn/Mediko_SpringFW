package si.fri.t15.validators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class CreateMedicalWorkerForm {
	@NotNull
	@Email
    private String email;

    @NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String password;
    
    @NotNull
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String repeatpassword;
    
    @NotNull
    @Size(min=4, max=24)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{4,24}$/")
    private String title;
    
	@Pattern(regexp="\b(Doctor|Nurse)\b")
    private String type;
    
    @NotNull
    @Size(min=2, max=16)
	@Pattern(regexp="/^[a-zA-Z ]{2,16}$/")
    private String first_name;
	
	@NotNull
	@Size(min=2, max=16)
	@Pattern(regexp="/^[a-zA-Z ]{2,24}$/")
    private String last_name;
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatpassword() {
		return repeatpassword;
	}

	public void setRepeatpassword(String repeatpassword) {
		this.repeatpassword = repeatpassword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
