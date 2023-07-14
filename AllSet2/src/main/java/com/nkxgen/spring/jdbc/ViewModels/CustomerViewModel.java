package com.nkxgen.spring.jdbc.ViewModels;

public class CustomerViewModel {

	private Long id;

	private String title;

	private String type;

	private String currentAddress;

	private String currentPINCode;

	private String dateOfBirth;

	private String mobile1;

	private String mobile2;

	private String residencePhone;

	private String residenceAddress;
	private String Otherloans;

	private String AnnualInccome;

	public String getOtherloans() {
		return Otherloans;
	}

	public void setOtherloans(String otherloans) {
		this.Otherloans = otherloans;
	}

	public String getAnnualInccome() {
		return AnnualInccome;
	}

	public void setAnnualInccome(String annualInccome) {
		this.AnnualInccome = annualInccome;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void setCurrentPINCode(String currentPINCode) {
		this.currentPINCode = currentPINCode;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public void setResidencePhone(String residencePhone) {
		this.residencePhone = residencePhone;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	// Setters and Getters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setcustTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setcust_type(String type) {
		this.type = type;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setcust_caddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getCurrentPINCode() {
		return currentPINCode;
	}

	public void setcust_capincode(String currentPINCode) {
		this.currentPINCode = currentPINCode;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setcust_dob(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setcust_mobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setcust_mobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getResidencePhone() {
		return residencePhone;
	}

	public void setcust_rphone(String residencePhone) {
		this.residencePhone = residencePhone;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setcust_raddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

}
