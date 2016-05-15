package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InsertReasonValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsertReasonForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "field.required", "Zahtevano polje");
		InsertReasonForm r = (InsertReasonForm) target;
		if(r.getReason().equals("/")){
			r.setReason("Neznan vzrok!");
		}			
	}
}
