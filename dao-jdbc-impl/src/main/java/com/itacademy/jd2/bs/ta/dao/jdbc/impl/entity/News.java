package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;

public class News extends BaseEntity implements INews {

	private String name;
	private String image;
	private String text;
	private Integer version;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getImage() {
		return image;
	}

	@Override
	public void setImage(final String image) {
		this.image = image;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "News [name=" + name + ", image=" + image + ", text=" + text + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

}
