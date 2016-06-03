package si.fri.t15.validators;

import org.springframework.validation.Validator;
import org.apache.commons.validator.GenericValidator;
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
		
		if(u.containsProfileData()){
			ValidationUtils.rejectIfEmpty(errors, "cardNumber", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "firstName", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "lastName", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "address", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "sex", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "birth", "filed.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "pobox", "field.required","Zahtevano polje" );
			ValidationUtils.rejectIfEmpty(errors, "contactFirstName", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "contactLastName", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "contactAddress", "field.required", "Zahtevano polje");
			ValidationUtils.rejectIfEmpty(errors, "contactPhoneNumber", "field.required", "Zahtevano polje");
			
			if(!GenericValidator.isDate(u.getBirth(), "yyyy-MM-dd", false)){
				errors.rejectValue("birth", "field.format","Napačen format datuma");
			}
		}
	}		
}
