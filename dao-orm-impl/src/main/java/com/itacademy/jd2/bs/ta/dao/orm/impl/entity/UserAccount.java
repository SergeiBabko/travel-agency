package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;

@Entity
public class UserAccount extends BaseEntity implements IUserAccount {

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userAccount", targetEntity = Customer.class)
	private ICustomer customer;

	@Column
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	@Override
	public UserRole getRole() {
		return role;
	}

	@Override
	public void setRole(final UserRole role) {
		this.role = role;
	}

	public ICustomer getCustomer() {
		return customer;
	}

	public void setCustomer(final ICustomer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "UserAccount [email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", role=" + role + ", getId()=" + getId() + ", getCreated()=" + getCreated()
				+ ", getUpdated()=" + getUpdated() + "]";
	}

}
