package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;

@Entity
public class News extends BaseEntity implements INews {

	@Column
	@Version
	private Integer version;

	@Column
	private String name;

	@Column
	private String image;

	@Column
	private String text;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "News [name=" + name + ", image=" + image + ", text=" + text + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
