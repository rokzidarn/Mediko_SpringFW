package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InsertDiseaseValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsertDiseaseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "idisease", "field.required", "Zahtevano polje");
	}
}
