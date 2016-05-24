package si.fri.t15.validators;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class InsAddDietValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(SignUpForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "diet", "field.required", "Vnesite veljavno dieto");
		ValidationUtils.rejectIfEmpty(errors, "instruction_diet", "field.required", "Vnesite navodilo");		
	}		
}
