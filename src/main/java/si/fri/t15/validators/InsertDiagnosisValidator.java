package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InsertDiagnosisValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsertDiagnosisForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "idiagnosis", "field.required", "Zahtevano polje!");
		
		InsertDiagnosisForm d = (InsertDiagnosisForm) target;
		
		if (d.getIdiagnosis().equals("/")) {
			d.setIdiagnosis("Ni navodil!");
		}		
	}
}
