package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;

@Entity
public class City extends BaseEntity implements ICity {

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
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
