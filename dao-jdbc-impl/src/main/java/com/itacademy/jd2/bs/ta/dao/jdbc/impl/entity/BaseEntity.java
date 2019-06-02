package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import java.util.Date;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBaseEntity;

public abstract class BaseEntity implements IBaseEntity {

	private Integer id;
	private Date created;
	private Date updated;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(final Integer id) {
		this.id = id;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public void setCreated(final Date created) {
		this.created = created;
	}

	@Override
	public Date getUpdated() {
		return updated;
	}

	@Override
	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

}
