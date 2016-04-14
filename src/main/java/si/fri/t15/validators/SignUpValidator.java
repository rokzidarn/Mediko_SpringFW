package si.fri.t15.validators;

import org.springframework.validation.Validator;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class SignUpValidator implements Validator{

	private static final int MINIMUM_PASSWORD_LENGTH = 6;

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
		
		if (!validateEmail(u.getUsername())) {
			errors.rejectValue("username", "field.format",
					"Incorrect email format");
		}

		if (u.getPassword() != null
				&& u.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
			errors.rejectValue("password", "field.min.length",
					new Object[] { Integer.valueOf(MINIMUM_PASSWORD_LENGTH) },
					"The password must be at least [" + MINIMUM_PASSWORD_LENGTH
							+ "] characters in length.");
		}
	}
	
	private boolean validateEmail(String email) {
		 
		if (email == null) {
			return false; // email is empty
		}
 
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
 
		// Match the given string with the pattern
		Matcher m = p.matcher(email);
 
		// check whether match is found
		boolean matchFound = m.matches();
 
		StringTokenizer st = new StringTokenizer(email, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}
 
		return matchFound && lastToken.length() >= 2
				&& email.length() - 1 != lastToken.length();
	}
}
