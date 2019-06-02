package com.itacademy.jd2.bs.ta.dao.api.entity;

public interface ICity extends IBaseEntity {

	String getName();

	void setName(String name);

	ICountry getCountry();

	void setCountry(ICountry country);

}
