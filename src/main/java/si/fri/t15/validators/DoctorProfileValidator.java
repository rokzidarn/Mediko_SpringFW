package si.fri.t15.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import si.fri.t15.doctor.controllers.DoctorProfileForm;

public class DoctorProfileValidator implements Validator {
	private static final String FNAME = "^[a-zA-Z ]{2,16}$";
	private static final String LNAME = "^[a-zA-Z ]{2,24}$";
	private static final String TITLE = "^[0-9a-zA-Z-_]{4,24}$";

	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(DoctorProfileForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DoctorProfileForm u = (DoctorProfileForm) target;

		ValidationUtils.rejectIfEmpty(errors, "title", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "sizz", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "first_name", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "last_name", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "maxPatients", "field.required", "Zahtevano polje.");

		Pattern pattern;
		Matcher matcher;

		if (!(u.getFirst_name() != null && u.getFirst_name().isEmpty())) {
			pattern = Pattern.compile(FNAME);
			matcher = pattern.matcher(u.getFirst_name());
			if (!matcher.matches()) {
				errors.rejectValue("first_name", "name.containNonChar", "Enter a valid first name");
			}
		}

		if (!(u.getLast_name() != null && u.getLast_name().isEmpty())) {
			pattern = Pattern.compile(LNAME);
			matcher = pattern.matcher(u.getLast_name());
			if (!matcher.matches()) {
				errors.rejectValue("last_name", "name.containNonChar", "Enter a valid last name");
			}
		}

		if (!(u.getTitle() != null && u.getTitle().isEmpty())) {
			pattern = Pattern.compile(TITLE);
			matcher = pattern.matcher(u.getTitle());
			if (!matcher.matches()) {
				errors.rejectValue("title", "name.containNonChar", "Enter a valid title");
			}
		}
	}
}
