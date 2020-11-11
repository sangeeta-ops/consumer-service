package com.prokarma.consumer.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.prokarma.consumer.util.CustomerStatusEnum;

@Entity(name = "Customer")
public class CustomerEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true, length = 50)
	private String uuid;

	@Column(name = "customer_number")
	private String customerNumber = null;

	@Column(name = "first_name")
	private String firstName = null;

	@Column(name = "last_name")
	private String lastName = null;

	@Column(name = "birth_date")
	private LocalDate birthDate = null;

	@Column(name = "country")
	private String country = null;

	@Column(name = "country_code")
	private String countryCode = null;

	@Column(name = "mobile_number")
	private Integer mobileNumber = null;

	@Column(name = "email")
	private String email = null;

	@Enumerated(EnumType.STRING)
	private CustomerStatusEnum customerStatusEnum;

	@OneToOne(targetEntity = AddressEntity.class, cascade = CascadeType.ALL)
	private AddressEntity address;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Integer mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustomerStatusEnum getCustomerStatusEnum() {
		return customerStatusEnum;
	}

	public void setCustomerStatusEnum(CustomerStatusEnum customerStatusEnum) {
		this.customerStatusEnum = customerStatusEnum;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

}
