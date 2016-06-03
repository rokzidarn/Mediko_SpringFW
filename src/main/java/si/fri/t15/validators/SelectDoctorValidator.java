package si.fri.t15.validators;

import org.springframework.validation.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class SelectDoctorValidator implements Validator{


	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(SelectDoctorForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "doctor", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "dentist", "field.required", "Zahtevano polje");
	}
}
