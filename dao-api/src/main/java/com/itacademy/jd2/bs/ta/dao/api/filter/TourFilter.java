package com.itacademy.jd2.bs.ta.dao.api.filter;

public class TourFilter extends AbstractFilter {

	private Integer countryId;
	private Integer cityId;
	private Integer tourTypeId;
	private Integer nightsMin;
	private Integer nightsMax;
	private Integer priceMin;
	private Integer priceMax;

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(final Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(final Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getTourTypeId() {
		return tourTypeId;
	}

	public void setTourTypeId(final Integer tourTypeId) {
		this.tourTypeId = tourTypeId;
	}

	public Integer getNightsMin() {
		return nightsMin;
	}

	public void setNightsMin(final Integer nightsMin) {
		this.nightsMin = nightsMin;
	}

	public Integer getNightsMax() {
		return nightsMax;
	}

	public void setNightsMax(final Integer nightsMax) {
		this.nightsMax = nightsMax;
	}

	public Integer getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(final Integer priceMin) {
		this.priceMin = priceMin;
	}

	public Integer getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(final Integer priceMax) {
		this.priceMax = priceMax;
	}

}
