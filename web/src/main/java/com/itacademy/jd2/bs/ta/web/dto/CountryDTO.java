package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class CountryDTO {

	private Integer id;
	private Date created;
	private Date updated;

	@NotEmpty
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}