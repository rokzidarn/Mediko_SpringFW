package si.fri.t15.validators;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class InsAddDiseaseValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsAddDiseaseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "disease", "field.required", "Vnesite veljavno bolezen");
		ValidationUtils.rejectIfEmpty(errors, "instruction", "field.required", "Vnesite navodilo");			
	}
}
