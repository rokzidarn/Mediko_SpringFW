package si.fri.t15.validators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreatePatientForm {
	
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
	@Size(max=4,min=4)
	private int pobox;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
