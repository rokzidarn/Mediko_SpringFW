package si.fri.t15.validators;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class SignUpValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(SignUpForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "username", "field.required", "Required field");
		ValidationUtils.rejectIfEmpty(errors, "password", "field.required", "Required field");		
		ValidationUtils.rejectIfEmpty(errors, "repeatpassword", "field.required", "Required field");
		
		SignUpForm u = (SignUpForm) target;
		
		//Problem maker
		if (u.getRepeatpassword() != null && u.getPassword() != null && !u.getRepeatpassword().equals(u.getPassword())) {
			errors.rejectValue("passwordConfirmation", "field.format",
					"Passwords do not match");
		}
	}		
}
