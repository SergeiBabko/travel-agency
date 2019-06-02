package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

public class BookedDTO {

	private Integer id;
	private Date created;
	private Date updated;

	private Integer customerId;
	private Integer tourDateId;
	private Integer numPerson;
	private Integer price;
	private String message;
	private Boolean processed;
	private String tourName;
	private Integer tourId;
	private String tourImage;
	private Date tourDateStart;
	private Date tourDateEnd;
	private Integer tourNights;

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

	public Integer getTourDateId() {
		return tourDateId;
	}

	public void setTourDateId(final Integer tourDateId) {
		this.tourDateId = tourDateId;
	}

	public Integer getNumPerson() {
		return numPerson;
	}

	public void setNumPerson(final Integer numPerson) {
		this.numPerson = numPerson;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(final Integer price) {
		this.price = price;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(final Boolean processed) {
		this.processed = processed;
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

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(final Integer tourId) {
		this.tourId = tourId;
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

	public Integer getTourNights() {
		return tourNights;
	}

	public void setTourNights(final Integer tourNights) {
		this.tourNights = tourNights;
	}

}
