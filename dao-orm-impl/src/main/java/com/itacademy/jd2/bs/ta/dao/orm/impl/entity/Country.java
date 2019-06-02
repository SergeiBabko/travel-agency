package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;

@Entity
public class Country extends BaseEntity implements ICountry {

	@Column
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
