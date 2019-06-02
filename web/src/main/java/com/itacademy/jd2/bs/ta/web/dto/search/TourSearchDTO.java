package com.itacademy.jd2.bs.ta.web.dto.search;

public class TourSearchDTO {

	private String countryId;
	private String cityId;
	private String tourTypeId;
	private String nights;
	private String price;

	public String getPrice() {
		return price;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	public String getTourTypeId() {
		return tourTypeId;
	}

	public void setTourTypeId(final String tourTypeId) {
		this.tourTypeId = tourTypeId;
	}

	public String getNights() {
		return nights;
	}

	public void setNights(final String nights) {
		this.nights = nights;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(final String countryId) {
		this.countryId = countryId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(final String cityId) {
		this.cityId = cityId;
	}

}
