package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DeleteMedicineValidator implements Validator {
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(DeleteMedicineForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "idm", "field.required", "Zahtevana izbira zdravila!");
	}
}
