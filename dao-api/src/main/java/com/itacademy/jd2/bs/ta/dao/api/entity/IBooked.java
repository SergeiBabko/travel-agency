package com.itacademy.jd2.bs.ta.dao.api.entity;

public interface IBooked extends IBaseEntity {

	ICustomer getCustomer();

	void setCustomer(ICustomer customer);

	ITourDate getTourDate();

	void setTourDate(ITourDate tourDate);

	Integer getNumPerson();

	void setNumPerson(Integer numPerson);

	Integer getPrice();

	void setPrice(Integer price);

	String getMessage();

	void setMessage(String message);

	Boolean getProcessed();

	void setProcessed(Boolean processed);
}
