package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DeleteDietValidator implements Validator {
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(DeleteDietForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "diname", "field.required", "Zahtevana izbira diete!");
	}
}
