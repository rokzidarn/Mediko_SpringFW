package si.fri.t15.validators;

import org.springframework.validation.Validator;

import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CreatePatientValidator implements Validator{

	private static final int MINIMUM_PASSWORD_LENGTH = 6;

	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(PatientProfileForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "cardNumber", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "firstName", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "address", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "sex", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "birth", "filed.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "pobox", "field.required","Zahtevano polje" );
		ValidationUtils.rejectIfEmpty(errors, "contactFirstName", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "contactLastName", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "contactAddress", "field.required", "Zahtevano polje");
		ValidationUtils.rejectIfEmpty(errors, "contactPhoneNumber", "field.required", "Zahtevano polje");
		
		PatientProfileForm u = (PatientProfileForm) target;
		
		if(!GenericValidator.isDate(u.getBirth(), "yyyy-MM-dd", false)){
			errors.rejectValue("birth", "field.format","Napačen format datuma");
		}
		else if (Date.valueOf(u.getBirth()).after(Calendar.getInstance().getTime())) {
			errors.rejectValue("birth", "field.format", "Datum ne sme biti v prihodnosti");
		}
		
	}
}
