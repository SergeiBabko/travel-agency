package com.itacademy.jd2.bs.ta.dao.api.entity;

public interface INews extends IBaseEntity {

	String getName();

	void setName(String name);

	String getImage();

	void setImage(String image);

	String getText();

	void setText(String text);

	Integer getVersion();

	void setVersion(Integer version);

}
