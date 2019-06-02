package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;

public class CustomerDTO {

	private Integer id;
	private Date created;
	private Date updated;

	private String email;
	private String firstName;
	private String lastName;
	private UserRole role;

	private CustomerStatus status;
	private String middleName;
	private String birthday;
	private String passportNumber;
	private String passportStart;
	private String passportEnd;
	private String phoneNumber;
	private String country;
	private String city;
	private String street;
	private Integer bonus;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(final UserRole role) {
		this.role = role;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(final CustomerStatus status) {
		this.status = status;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(final String birthday) {
		this.birthday = birthday;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(final String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassportStart() {
		return passportStart;
	}

	public void setPassportStart(final String passportStart) {
		this.passportStart = passportStart;
	}

	public String getPassportEnd() {
		return passportEnd;
	}

	public void setPassportEnd(final String passportEnd) {
		this.passportEnd = passportEnd;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(final Integer bonus) {
		this.bonus = bonus;
	}

}