package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddDiseaseValidator implements Validator {
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(AddDiseaseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "did", "field.required", "Zahtevan ID bolezni!");
		ValidationUtils.rejectIfEmpty(errors, "dname", "field.required", "Zahtevano ime bolezni!");
		ValidationUtils.rejectIfEmpty(errors, "dtype", "field.required", "Zahtevan tip bolezni!");
		
		AddDiseaseForm u = (AddDiseaseForm) target;
		if(u.getDtype()>1 || u.getDtype()<0){
			errors.rejectValue("dtype", "field.format",
					"Neustrezen tip bolezni - alergija/bolezen!");
		}
	}
}
