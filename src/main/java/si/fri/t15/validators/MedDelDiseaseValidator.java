package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MedDelDiseaseValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(MedDelDiseaseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "disease", "field.required", "Izberi ustrezno bolezen");
		ValidationUtils.rejectIfEmpty(errors, "medicine", "field.required", "Izberi ustrezno zdravilo");
		
		MedDelDiseaseForm u = (MedDelDiseaseForm) target;
		
		if(u.getDisease().equals("")){
			errors.rejectValue("disease", "field.format",
					"Neveljavna bolezen!");
		}
		
		if(u.getMedicine()<=0){
			errors.rejectValue("medicine", "field.format",
					"Neveljavno zdravilo!");
		}
	}
}
