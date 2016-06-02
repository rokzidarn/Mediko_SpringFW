package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DeleteDiseaseValidator implements Validator {
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(DeleteDiseaseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "idd", "field.required", "Zahtevana izbira bolezni!");
	}
}
