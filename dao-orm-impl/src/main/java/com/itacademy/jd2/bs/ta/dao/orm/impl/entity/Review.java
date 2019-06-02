package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;

@Entity
public class Review extends BaseEntity implements IReview {

	@Column
	private String review;

	@Column
	private Integer rating;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	private ICustomer customer;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = TourDate.class)
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
