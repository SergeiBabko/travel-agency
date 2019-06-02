package com.itacademy.jd2.bs.ta.dao.api.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;

public interface IUserAccount extends IBaseEntity {

	String getEmail();

	void setEmail(String email);

	String getPassword();

	void setPassword(String password);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	UserRole getRole();

	void setRole(UserRole role);

	ICustomer getCustomer();

	void setCustomer(ICustomer customer);

}
