package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;

@Entity
public class Customer implements ICustomer {

	@Id
	private Integer id;

	@Column(updatable = false)
	private Date created;

	@Column
	private Date updated;

	@Column
	@Enumerated(EnumType.STRING)
	private CustomerStatus status;

	@Column
	private String middleName;

	@Column
	private String birthday;

	@Column
	private String passportNumber;

	@Column
	private String passportStart;

	@Column
	private String passportEnd;

	@Column
	private String phoneNumber;

	@Column
	private String country;

	@Column
	private String city;

	@Column
	private String street;

	@Column
	private Integer bonus;

	@OneToOne(fetch = FetchType.LAZY, optional = false, targetEntity = UserAccount.class)
	@PrimaryKeyJoinColumn
	private IUserAccount userAccount;

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
		return "Customer [id=" + id + ", created=" + created + ", updated=" + updated + ", status=" + status + ", role="
				+ userAccount.getRole().name() + ", middleName=" + middleName + ", birthday=" + birthday
				+ ", passportNumber=" + passportNumber + ", passportStart=" + passportStart + ", passportEnd="
				+ passportEnd + ", phoneNumber=" + phoneNumber + ", country=" + country + ", city=" + city + ", street="
				+ street + ", bonus=" + bonus + ", userAccount=" + userAccount + "]";
	}

}
