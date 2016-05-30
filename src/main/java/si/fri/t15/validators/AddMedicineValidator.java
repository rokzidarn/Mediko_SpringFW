package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddMedicineValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(AddMedicineForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "mname", "field.required", "Zahtevano ime zdravila!");
	}
}
