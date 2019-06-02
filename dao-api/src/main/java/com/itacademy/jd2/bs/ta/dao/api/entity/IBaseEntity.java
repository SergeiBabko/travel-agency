package com.itacademy.jd2.bs.ta.dao.api.entity;

import java.util.Date;

public interface IBaseEntity {
	
	Integer getId();

	void setId(Integer id);

	Date getCreated();

	void setCreated(Date created);

	Date getUpdated();

	void setUpdated(Date updated);

}