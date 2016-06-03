package si.fri.t15.validators;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CreateMedicalWorkerValidator implements Validator {
	private static final int MINIMUM_PASSWORD_LENGTH = 6;
	private static final String FNAME = "^[a-zA-Z ]{2,16}$";
	private static final String LNAME = "^[a-zA-Z ]{2,24}$";
	private static final String TITLE = "^[0-9a-zA-Z-_]{4,24}$";

	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(CreateMedicalWorkerForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateMedicalWorkerForm u = (CreateMedicalWorkerForm) target;

		ValidationUtils.rejectIfEmpty(errors, "email", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "password", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "repeatpassword", "field.required", "Zahtevano polje.");
		ValidationUtils.rejectIfEmpty(errors, "type", "field.required", "Zahtevano polje.");

		if (!u.getPassword().equals(u.getRepeatpassword())) {
			errors.rejectValue("password", "field.format", "Gesli se ne ujemata.");
		}

		if (!(u.getType().equals("Nurse") || u.getType().equals("Doctor"))) {
			errors.rejectValue("type", "field.format", "Izberite vrsto delavca.");
		}

		if (!validateEmail(u.getEmail())) {
			errors.rejectValue("email", "field.format", "Incorrect email format");
		}

		if (u.getPassword() != null && u.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
			errors.rejectValue("password", "field.min.length",
					new Object[] { Integer.valueOf(MINIMUM_PASSWORD_LENGTH) },
					"The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
		}

		if (u.containsProfileData()) {
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

	private boolean validateEmail(String email) {

		if (email == null) {
			return false; // email is empty
		}

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

		// Match the given string with the pattern
		Matcher m = p.matcher(email);

		// check whether match is found
		boolean matchFound = m.matches();

		StringTokenizer st = new StringTokenizer(email, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}

		return matchFound && lastToken.length() >= 2 && email.length() - 1 != lastToken.length();
	}
}
