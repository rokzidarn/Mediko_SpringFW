package si.fri.t15.validators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PatientProfileForm {
	
	@NotNull
    @Pattern(regexp="/^[0-9]{9}$/")
    private String cardNumber;
	
	@NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String firstName;
	
	@NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String lastName;
	
	@NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String contactFirstName;
	
	@NotNull
    @Size(min=6, max=16)
    @Pattern(regexp="/^[0-9a-zA-Z-_]{6,16}$/")
    private String contactLastName;
	
	@NotNull
	@Pattern(regexp="/[fm]{1}/")
	private char sex;

	@NotNull
	@Size(min=10,max=10)
	@Pattern(regexp="/[0-9]{4}-[0-9]{2}-[0-9]{2}/")
	private String birth;
	
	@NotNull
	@Size(min=2,max=100)
	@Pattern(regexp="/^*{2,100}$/")
	private String address;
	
	@NotNull
	@Size(min=2,max=100)
	@Pattern(regexp="/^*{2,100}$/")
	private String contactAddress;
	
	@NotNull
	@Size(min=9, max=9)
	@Pattern(regexp="/^.*$/")
    private String phoneNumber;
	
	@NotNull
	@Size(min=9, max=9)
	@Pattern(regexp="/^.*$/")
    private String contactPhoneNumber;
	
	@NotNull
	@Size(max=4,min=4)
	private int pobox;
	
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

	public int getPobox() {
		return pobox;
	}

	public void setPobox(int pobox) {
		this.pobox = pobox;
	}

	

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
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
	
	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
}
