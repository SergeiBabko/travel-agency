package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;

public class Booked extends BaseEntity implements IBooked {

	private ICustomer customer;
	private ITourDate tourDate;
	private Integer numPerson;
	private Integer price;
	private String message;
	private Boolean processed;

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
	public Integer getNumPerson() {
		return numPerson;
	}

	@Override
	public void setNumPerson(final Integer numPerson) {
		this.numPerson = numPerson;
	}

	@Override
	public Integer getPrice() {
		return price;
	}

	@Override
	public void setPrice(final Integer price) {
		this.price = price;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public Boolean getProcessed() {
		return processed;
	}

	@Override
	public void setProcessed(final Boolean processed) {
		this.processed = processed;
	}

	@Override
	public String toString() {
		return "Booked [customer=" + customer.getId() + ", tourDate=" + tourDate.getId() + ", numPerson=" + numPerson
				+ ", price=" + price + ", message=" + message + ", processed=" + processed + ", getId()=" + getId()
				+ ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
