package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;

@Entity
public class TourType extends BaseEntity implements ITourType {

	@Column
	private String type;

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TourType [type=" + type + ", getId()=" + getId() + ", getCreated()=" + getCreated() + ", getUpdated()="
				+ getUpdated() + "]";
	}

}
