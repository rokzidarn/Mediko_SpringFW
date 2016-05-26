package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MedAddDiseaseValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(MedAddDiseaseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "disease", "field.required", "Izberi ustrezno bolezen");
		ValidationUtils.rejectIfEmpty(errors, "medicine", "field.required", "Izberi ustrezno zdravilo");
		
		MedAddDiseaseForm u = (MedAddDiseaseForm) target;
		
		if(u.getDisease().equals("")){
			errors.rejectValue("disease", "field.format",
					"Neveljavna bolezen!");
		}
		
		if(u.getMedicine()<0){
			errors.rejectValue("medicine", "field.format",
					"Neveljavno zdravilo!");
		}		
	}
}
