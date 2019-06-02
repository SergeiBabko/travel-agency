package com.itacademy.jd2.bs.ta.dao.api.entity;

import java.util.Date;

public interface ITourDate extends IBaseEntity {

	ITour getTour();

	void setTour(ITour tour);

	Integer getNumPerson();

	void setNumPerson(Integer numPerson);

	Date getDateStart();

	void setDateStart(Date dateStart);

	Date getDateEnd();

	void setDateEnd(Date dateEnd);
}
