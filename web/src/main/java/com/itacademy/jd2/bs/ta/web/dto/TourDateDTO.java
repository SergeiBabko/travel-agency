package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class TourDateDTO {

	private Integer id;
	private Date created;
	private Date updated;

	private Integer tourId;
	private Integer numPerson;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date dateStart;
	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date dateEnd;

	private String dateStartString;
	private String dateEndString;

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

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(final Integer tourId) {
		this.tourId = tourId;
	}

	public Integer getNumPerson() {
		return numPerson;
	}

	public void setNumPerson(final Integer numPerson) {
		this.numPerson = numPerson;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(final Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(final Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDateStartString() {
		return dateStartString;
	}

	public void setDateStartString(final String dateStartString) {
		this.dateStartString = dateStartString;
	}

	public String getDateEndString() {
		return dateEndString;
	}

	public void setDateEndString(final String dateEndString) {
		this.dateEndString = dateEndString;
	}

}