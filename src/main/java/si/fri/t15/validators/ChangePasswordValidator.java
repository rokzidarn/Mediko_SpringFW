package si.fri.t15.validators;

import org.springframework.validation.Validator;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ChangePasswordValidator implements Validator{

	private static final int MINIMUM_PASSWORD_LENGTH = 8;

	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(ChangePasswordForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "email", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "password", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "repeatpassword", "field.required", "Zahtevano polje");
		
		ChangePasswordForm u = (ChangePasswordForm) target;
		
		if (u.getPassword() != null
				&& u.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
			errors.rejectValue("password", "field.min.length",
					new Object[] { Integer.valueOf(MINIMUM_PASSWORD_LENGTH) },
					"Geslo mora vsebovati najmanj [" + MINIMUM_PASSWORD_LENGTH
							+ "] znakov.");
		}
		
		if (u.getRepeatpassword() != null && u.getPassword() != null && !u.getRepeatpassword().equals(u.getPassword())) {
			errors.rejectValue("repeatpassword", "field.format",
					"Gesli se ne ujemata.");
		}
		
		if(u.getPassword() != null){
			if(u.getPassword().indexOf('0') != -1 ||
					u.getPassword().indexOf('1') != -1 ||
					u.getPassword().indexOf('2') != -1 ||
					u.getPassword().indexOf('3') != -1 ||
					u.getPassword().indexOf('4') != -1 ||
					u.getPassword().indexOf('5') != -1 ||
					u.getPassword().indexOf('6') != -1 ||
					u.getPassword().indexOf('7') != -1 ||
					u.getPassword().indexOf('8') != -1 ||
					u.getPassword().indexOf('9') != -1)
			{	
			}else{
				errors.rejectValue("password", "field.format",
						"Geslo ne vsebuje številk.");
			}
		}
		
	}
}
