package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;

public class Favorite extends BaseEntity implements IFavorite {

	private ICustomer customer;
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
