package com.itacademy.jd2.bs.ta.dao.api.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;

public interface ICustomer extends IBaseEntity {

	CustomerStatus getStatus();

	void setStatus(CustomerStatus status);

	String getMiddleName();

	void setMiddleName(String middleName);

	String getBirthday();

	void setBirthday(String birthday);

	String getPassportNumber();

	void setPassportNumber(String passportNumber);

	String getPassportStart();

	void setPassportStart(String passportStart);

	String getPassportEnd();

	void setPassportEnd(String passportEnd);

	String getPhoneNumber();

	void setPhoneNumber(String phoneNumber);

	String getCountry();

	void setCountry(String country);

	String getCity();

	void setCity(String city);

	String getStreet();

	void setStreet(String street);

	Integer getBonus();

	void setBonus(Integer bonus);

	IUserAccount getUserAccount();

	void setUserAccount(IUserAccount userAccount);

}
