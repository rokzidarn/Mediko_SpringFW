package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InsertMedicineValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsertMedicineForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "imedicine", "field.required", "Zahtevano polje");
		
		InsertMedicineForm m = (InsertMedicineForm) target;
		
		if (m.getImedicine()<1) {
			errors.rejectValue("imedicine", "field.format",
					"Invalid medicine");
		}		
	}
}
