package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

public class FavoriteDTO {

	private Integer id;
	private Date created;
	private Date updated;

	private Integer customerId;
	private Integer tourId;

	private String tourName;
	private String tourImage;
	private Integer tourNights;
	private Integer tourPrice;

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(final Integer tourId) {
		this.tourId = tourId;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(final String tourName) {
		this.tourName = tourName;
	}

	public String getTourImage() {
		return tourImage;
	}

	public void setTourImage(final String tourImage) {
		this.tourImage = tourImage;
	}

	public Integer getTourNights() {
		return tourNights;
	}

	public void setTourNights(final Integer tourNights) {
		this.tourNights = tourNights;
	}

	public Integer getTourPrice() {
		return tourPrice;
	}

	public void setTourPrice(final Integer tourPrice) {
		this.tourPrice = tourPrice;
	}

}
