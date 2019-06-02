package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class TaDescriptionDTO {

	private Integer id;
	private Date created;
	private Date updated;

	@NotEmpty
	private String description;
	@NotEmpty
	private String contacts;
	@NotEmpty
	private String address;
	@NotEmpty
	private String image1;
	@NotEmpty
	private String image2;
	@NotEmpty
	private String image3;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(final String contacts) {
		this.contacts = contacts;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(final String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(final String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(final String image3) {
		this.image3 = image3;
	}

}