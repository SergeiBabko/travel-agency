package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;

public class UserAccount extends BaseEntity implements IUserAccount {

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private UserRole role;
	private ICustomer customer;

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
				+ lastName + ", role=" + role + ", customer=" + customer.getId() + ", getId()=" + getId()
				+ ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
