package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;

public class TaDescription extends BaseEntity implements ITaDescription {

	private String description;
	private String contacts;
	private String address;
	private String image1;
	private String image2;
	private String image3;

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String getContacts() {
		return contacts;
	}

	@Override
	public void setContacts(final String contacts) {
		this.contacts = contacts;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(final String address) {
		this.address = address;
	}

	@Override
	public String getImage1() {
		return image1;
	}

	@Override
	public void setImage1(final String image1) {
		this.image1 = image1;
	}

	@Override
	public String getImage2() {
		return image2;
	}

	@Override
	public void setImage2(final String image2) {
		this.image2 = image2;
	}

	@Override
	public String getImage3() {
		return image3;
	}

	@Override
	public void setImage3(final String image3) {
		this.image3 = image3;
	}

	@Override
	public String toString() {
		return "TaDescription [description=" + description + ", contacts=" + contacts + ", address=" + address
				+ ", image1=" + image1 + ", image2=" + image2 + ", image3=" + image3 + ", getId()=" + getId()
				+ ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
