package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;

public class Country extends BaseEntity implements ICountry {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", getId()=" + getId() + ", getCreated()=" + getCreated() + ", getUpdated()="
				+ getUpdated() + "]";
	}

}
