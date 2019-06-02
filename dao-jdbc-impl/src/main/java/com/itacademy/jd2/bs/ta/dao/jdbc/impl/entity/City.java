package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;

public class City extends BaseEntity implements ICity {

	private String name;
	private ICountry country;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public ICountry getCountry() {
		return country;
	}

	@Override
	public void setCountry(final ICountry country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", country=" + country.getId() + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
