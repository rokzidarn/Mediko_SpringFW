package si.fri.t15.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InsertResultValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(InsertResultForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "itype", "field.required", "Vnesi tip meritve!");	
		ValidationUtils.rejectIfEmpty(errors, "iresult", "field.required", "Vnesi rezultat meritve!");
		ValidationUtils.rejectIfEmpty(errors, "iresult", "field.required", "Vnesi komentar!");
				
		InsertResultForm r = (InsertResultForm) target;
		
		if(!r.getItype().equals("Krvni tlak") && !r.getItype().equals("Glukoza")){
			errors.rejectValue("itype", "field.format",
					"Izberi ustrezen tip meritve");
		}
		
		else if(r.getItype().equals("Glukoza") && (Float.parseFloat(r.getIresult())<2 || Float.parseFloat(r.getIresult())>25)){
			errors.rejectValue("iresult", "field.format",
					"Neveljavna meritev glukoze!");
		}
	}
}
