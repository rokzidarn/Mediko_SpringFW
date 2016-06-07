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
		ValidationUtils.rejectIfEmpty(errors, "itext", "field.required", "Vnesi rezultat meritve!");
		ValidationUtils.rejectIfEmpty(errors, "iresult", "field.required", "Vnesi komentar!");
				
		InsertResultForm r = (InsertResultForm) target;
		
		if(!r.getItype().equals("Krvni tlak") && !r.getItype().equals("Glukoza") && !r.getItype().equals("Holesterol") 
				&& !r.getItype().equals("Temperatura") && !r.getItype().equals("Teža")){
			errors.rejectValue("itype", "field.format",
					"Izberi ustrezen tip meritve");
		}
		
		if(r.getItype().equals("Glukoza") && (Float.parseFloat(r.getIresult())<2 || Float.parseFloat(r.getIresult())>25)){
			errors.rejectValue("iresult", "field.format",
					"Neveljavna meritev glukoze!");
		}
		
		if(r.getItype().equals("Teža") && (Float.parseFloat(r.getIresult())<0 || Float.parseFloat(r.getIresult())>300)){
			errors.rejectValue("iresult", "field.format",
					"Neveljavna meritev teže!");
		}
		
		if(r.getItype().equals("Temperatura") && (Float.parseFloat(r.getIresult())<35.5 || Float.parseFloat(r.getIresult())>42.5)){
			errors.rejectValue("iresult", "field.format",
					"Neveljavna meritev temperature!");
		}
		
		if(r.getItype().equals("Holesterol")){
			String[] holesterol = r.getIresult().split("/");
			if(Float.parseFloat(holesterol[0])>5 || Float.parseFloat(holesterol[1])>3 || Float.parseFloat(holesterol[2])<1.2)
				errors.rejectValue("iresult", "field.format",
						"Neveljavna meritev holesterola!");			
		}
		
		if(r.getItype().equals("Tlak")){
			String[] tlak = r.getIresult().split("/");
			if(Float.parseFloat(tlak[0])>220 || Float.parseFloat(tlak[1])>200 || Float.parseFloat(tlak[2])<30)
				errors.rejectValue("iresult", "field.format",
						"Neveljavna meritev tlaka!");			
		}				
	}
}
