package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;

@Entity
public class Favorite extends BaseEntity implements IFavorite {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	private ICustomer customer;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Tour.class)
	private ITour tour;

	@Override
	public ICustomer getCustomer() {
		return customer;
	}

	@Override
	public void setCustomer(final ICustomer customer) {
		this.customer = customer;
	}

	@Override
	public ITour getTour() {
		return tour;
	}

	@Override
	public void setTour(final ITour tour) {
		this.tour = tour;
	}

	@Override
	public String toString() {
		return "Favorite [customer=" + customer.getId() + ", tour=" + tour.getId() + ", getId()=" + getId()
				+ ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
