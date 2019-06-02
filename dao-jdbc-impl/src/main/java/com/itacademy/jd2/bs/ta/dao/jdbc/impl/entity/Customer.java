package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;

public class Customer extends BaseEntity implements ICustomer {

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
	private IUserAccount userAccount;

	@Override
	public CustomerStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(final CustomerStatus status) {
		this.status = status;
	}

	@Override
	public String getMiddleName() {
		return middleName;
	}

	@Override
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String getBirthday() {
		return birthday;
	}

	@Override
	public void setBirthday(final String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String getPassportNumber() {
		return passportNumber;
	}

	@Override
	public void setPassportNumber(final String passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Override
	public String getPassportStart() {
		return passportStart;
	}

	@Override
	public void setPassportStart(final String passportStart) {
		this.passportStart = passportStart;
	}

	@Override
	public String getPassportEnd() {
		return passportEnd;
	}

	@Override
	public void setPassportEnd(final String passportEnd) {
		this.passportEnd = passportEnd;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(final String country) {
		this.country = country;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(final String city) {
		this.city = city;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(final String street) {
		this.street = street;
	}

	@Override
	public IUserAccount getUserAccount() {
		return userAccount;
	}

	@Override
	public void setUserAccount(final IUserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Override
	public Integer getBonus() {
		return bonus;
	}

	@Override
	public void setBonus(final Integer bonus) {
		this.bonus = bonus;
	}

	@Override
	public String toString() {
		return "Customer [status=" + status + ", role=" + userAccount.getRole().name() + ", middleName=" + middleName
				+ ", birthday=" + birthday + ", passportNumber=" + passportNumber + ", passportStart=" + passportStart
				+ ", passportEnd=" + passportEnd + ", phoneNumber=" + phoneNumber + ", country=" + country + ", city="
				+ city + ", street=" + street + ", userAccount=" + userAccount + ", bonus=" + bonus + ", getId()="
				+ getId() + ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
