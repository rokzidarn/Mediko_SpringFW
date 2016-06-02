package si.fri.t15.validators;

import org.springframework.validation.Validator;

import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CreatePatientValidator implements Validator{

	private static final int MINIMUM_PASSWORD_LENGTH = 6;

	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(CreatePatientForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "address", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "sex", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "birth", "filed.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "pobox", "field.required","Zahtevano polje" );
		
		CreatePatientForm u = (CreatePatientForm) target;
		
		if(!GenericValidator.isDate(u.getBirth(), "yyyy-MM-dd", false)){
			errors.rejectValue("birth", "field.format","Napačen format datuma");
		}
		
	}
}
