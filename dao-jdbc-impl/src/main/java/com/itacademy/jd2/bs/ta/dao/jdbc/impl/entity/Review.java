package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;

public class Review extends BaseEntity implements IReview {

	private String review;
	private Integer rating;
	private ICustomer customer;
	private ITourDate tourDate;

	@Override
	public String getReview() {
		return review;
	}

	@Override
	public void setReview(final String review) {
		this.review = review;
	}

	@Override
	public Integer getRating() {
		return rating;
	}

	@Override
	public void setRating(final Integer rating) {
		this.rating = rating;
	}

	@Override
	public ICustomer getCustomer() {
		return customer;
	}

	@Override
	public void setCustomer(final ICustomer customer) {
		this.customer = customer;
	}

	@Override
	public ITourDate getTourDate() {
		return tourDate;
	}

	@Override
	public void setTourDate(final ITourDate tourDate) {
		this.tourDate = tourDate;
	}

	@Override
	public String toString() {
		return "Review [review=" + review + ", rating=" + rating + ", customer=" + customer.getId() + ", tourDate="
				+ tourDate.getId() + ", getId()=" + getId() + ", getCreated()=" + getCreated() + ", getUpdated()="
				+ getUpdated() + "]";
	}

}
