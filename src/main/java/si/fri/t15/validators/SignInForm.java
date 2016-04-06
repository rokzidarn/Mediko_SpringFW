package si.fri.t15.validators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignInForm {

	@NotNull
    @Size(min=2, max=16)
	@Pattern(regexp="/^[0-9a-zA-Z-_]{2,16}$/")
    private String username;

    @NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String password;

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
}
