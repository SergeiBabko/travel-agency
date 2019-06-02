package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;

public class TourType extends BaseEntity implements ITourType {

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
