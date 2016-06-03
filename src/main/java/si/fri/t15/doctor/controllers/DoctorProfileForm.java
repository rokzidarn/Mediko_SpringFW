package si.fri.t15.doctor.controllers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DoctorProfileForm {
	@NotNull
	@Size(min = 4, max = 24)
	@Pattern(regexp = "/^[0-9a-zA-Z-_]{4,24}$/")
	private String title;

	@NotNull
	@Size(min = 2, max = 16)
	@Pattern(regexp = "/^[a-zA-Z ]{2,16}$/")
	private String first_name;

	@NotNull
	@Size(min = 2, max = 16)
	@Pattern(regexp = "/^[a-zA-Z ]{2,24}$/")
	private String last_name;

	@NotNull
	@Size(min = 2, max = 16)
	@Pattern(regexp = "/^[0-9]*$/")
	private String sizz;

	@NotNull
	@Size(min = 9, max = 9)
	@Pattern(regexp = "/^.*$/")
	private String phoneNumber;

	@NotNull
	@Size(min = 50)
	@Pattern(regexp = "/^[0-9]*$/")
	private Integer maxPatients;

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSizz() {
		return sizz;
	}

	public void setSizz(String sizz) {
		this.sizz = sizz;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getMaxPatients() {
		return maxPatients;
	}

	public void setMaxPatients(Integer maxPatients) {
		this.maxPatients = maxPatients;
	}
}
