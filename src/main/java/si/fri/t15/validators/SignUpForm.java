package si.fri.t15.validators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;

public class SignUpForm {

	@NotNull
	@Email
	private String username;

	@NotNull
	@Size(min = 6, max = 16)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{6,16}$/")
	private String password;

	@NotNull
	@Size(min = 6, max = 16)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{6,16}$/")
	private String repeatpassword;

	@NotNull
	@Pattern(regexp = "/^[0-9]{9}$/")
	private String cardNumber;

	@NotNull
	@Size(min = 6, max = 16)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{6,16}$/")
	private String firstName;

	@NotNull
	@Size(min = 6, max = 16)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{6,16}$/")
	private String lastName;

	@NotNull
	@Size(min = 6, max = 16)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{6,16}$/")
	private String contactFirstName;

	@NotNull
	@Size(min = 6, max = 16)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{6,16}$/")
	private String contactLastName;

	@NotNull
	@Pattern(regexp = "/[fm]{1}/")
	private Character sex;

	@NotNull
	@Size(min = 10, max = 10)
	@Pattern(regexp = "/[0-9]{4}-[0-9]{2}-[0-9]{2}/")
	private String birth;

	@NotNull
	@Size(min = 2, max = 100)
	@Pattern(regexp = "/^*{2,100}$/")
	private String address;

	@NotNull
	@Size(min = 2, max = 100)
	@Pattern(regexp = "/^*{2,100}$/")
	private String contactAddress;

	@NotNull
	@Size(min = 9, max = 9)
	@Pattern(regexp = "/^.*$/")
	private String phoneNumber;

	@NotNull
	@Size(min = 9, max = 9)
	@Pattern(regexp = "/^.*$/")
	private String contactPhoneNumber;

	@NotNull
	@Size(max = 4, min = 4)
	private Integer pobox;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRepeatpassword() {
		return repeatpassword;
	}

	public void setRepeatpassword(String repeatpassword) {
		this.repeatpassword = repeatpassword;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public Integer getPobox() {
		return pobox;
	}

	public void setPobox(Integer pobox) {
		this.pobox = pobox;
	}

	public boolean containsProfileData() {
		return (StringUtils.isNotEmpty(cardNumber) || StringUtils.isNotEmpty(firstName)
				|| StringUtils.isNotEmpty(lastName) || StringUtils.isNotEmpty(contactFirstName)
				|| StringUtils.isNotEmpty(contactLastName) || sex != null || StringUtils.isNotEmpty(birth)
				|| StringUtils.isNotEmpty(address) || StringUtils.isNotEmpty(contactAddress)
				|| StringUtils.isNotEmpty(phoneNumber) || StringUtils.isNotEmpty(contactPhoneNumber) || pobox != null && pobox != 1000);
	}
}
