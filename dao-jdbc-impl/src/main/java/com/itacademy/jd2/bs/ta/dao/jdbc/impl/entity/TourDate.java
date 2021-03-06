package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import java.util.Date;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;

public class TourDate extends BaseEntity implements ITourDate {

	private ITour tour;
	private Integer numPerson;
	private Date dateStart;
	private Date dateEnd;

	@Override
	public ITour getTour() {
		return tour;
	}

	@Override
	public void setTour(final ITour tour) {
		this.tour = tour;
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
	public Date getDateStart() {
		return dateStart;
	}

	@Override
	public void setDateStart(final Date dateStart) {
		this.dateStart = dateStart;
	}

	@Override
	public Date getDateEnd() {
		return dateEnd;
	}

	@Override
	public void setDateEnd(final Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		return "TourDate [tour=" + tour.getId() + ", numPerson=" + numPerson + ", dateStart=" + dateStart + ", dateEnd="
				+ dateEnd + ", getId()=" + getId() + ", getCreated()=" + getCreated() + ", getUpdated()=" + getUpdated()
				+ "]";
	}

}
