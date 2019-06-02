package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ReviewDTO {

	private Integer id;
	private Date created;
	private Date updated;

	@NotEmpty
	private String review;
	@NotNull
	private Integer rating;
	private Integer customerId;
	@NotNull
	private Integer tourDateId;

	private String userFirstName;
	private String userLastName;
	private Date tourDateStart;
	private Date tourDateEnd;

	private Integer tourId;
	private String tourName;
	private String tourImage;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public String getReview() {
		return review;
	}

	public void setReview(final String review) {
		this.review = review;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(final Integer rating) {
		this.rating = rating;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getTourDateId() {
		return tourDateId;
	}

	public void setTourDateId(final Integer tourDateId) {
		this.tourDateId = tourDateId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(final String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(final String userLastName) {
		this.userLastName = userLastName;
	}

	public Date getTourDateStart() {
		return tourDateStart;
	}

	public void setTourDateStart(final Date tourDateStart) {
		this.tourDateStart = tourDateStart;
	}

	public Date getTourDateEnd() {
		return tourDateEnd;
	}

	public void setTourDateEnd(final Date tourDateEnd) {
		this.tourDateEnd = tourDateEnd;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(final String tourName) {
		this.tourName = tourName;
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(final Integer tourId) {
		this.tourId = tourId;
	}

	public String getTourImage() {
		return tourImage;
	}

	public void setTourImage(final String tourImage) {
		this.tourImage = tourImage;
	}

}