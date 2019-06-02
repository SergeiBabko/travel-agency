package com.itacademy.jd2.bs.ta.dao.api.entity;

public interface IReview extends IBaseEntity {

	String getReview();

	void setReview(String review);

	Integer getRating();

	void setRating(Integer rating);

	ICustomer getCustomer();

	void setCustomer(ICustomer customer);

	ITourDate getTourDate();

	void setTourDate(ITourDate tourDate);

}
