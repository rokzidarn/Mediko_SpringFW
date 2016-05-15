package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InsertDietValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsertDietForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "idiet", "field.required", "Zahtevano polje");
		
		InsertDietForm d = (InsertDietForm) target;
		
		if (d.getIdiet()<1) {
			errors.rejectValue("idiet", "field.format",
					"Invalid diet");
		}		
	}
}
