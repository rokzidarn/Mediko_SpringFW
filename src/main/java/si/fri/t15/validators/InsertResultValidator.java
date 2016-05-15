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
		
		if(!r.getItype().equals("Krvni tlak")){
			errors.rejectValue("itype", "field.format",
					"Izberi ustrezen tip meritve");
		}
		
		if(!r.getItype().equals("Krvni tlak")){
			errors.rejectValue("itype", "field.format",
					"Izberi ustrezen tip meritve");
		}
		
		if(r.getItype().equals("Glukoza") && (Integer.parseInt(r.getIresult())<2 || Integer.parseInt(r.getIresult())>25)){
			errors.rejectValue("iresult", "field.format",
					"Neveljavna meritev glukoze!");
		}
	}
}
