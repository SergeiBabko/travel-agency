package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;

@Entity
public class TaDescription extends BaseEntity implements ITaDescription {

	@Column
	private String description;

	@Column
	private String contacts;

	@Column
	private String address;

	@Column
	private String image_1;

	@Column
	private String image_2;

	@Column
	private String image_3;

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
		return image_1;
	}

	@Override
	public void setImage1(final String image1) {
		this.image_1 = image1;
	}

	@Override
	public String getImage2() {
		return image_2;
	}

	@Override
	public void setImage2(final String image2) {
		this.image_2 = image2;
	}

	@Override
	public String getImage3() {
		return image_3;
	}

	@Override
	public void setImage3(final String image3) {
		this.image_3 = image3;
	}

	@Override
	public String toString() {
		return "TaDescription [description=" + description + ", contacts=" + contacts + ", address=" + address
				+ ", image1=" + image_1 + ", image2=" + image_2 + ", image3=" + image_3 + ", getId()=" + getId()
				+ ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
