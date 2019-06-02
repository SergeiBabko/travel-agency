package com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;

public class Tour extends BaseEntity implements ITour {

	private TourStatus tourStatus;
	private String name;
	private Integer price;
	private ITourType tourType;
	private Integer nights;
	private String image;
	private String description;
	private ICity city;
	private String address;
	private Integer version;

	@Override
	public TourStatus getTourStatus() {
		return tourStatus;
	}

	@Override
	public void setTourStatus(final TourStatus tourStatus) {
		this.tourStatus = tourStatus;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
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
	public ITourType getTourType() {
		return tourType;
	}

	@Override
	public void setTourType(final ITourType tourType) {
		this.tourType = tourType;
	}

	@Override
	public Integer getNights() {
		return nights;
	}

	@Override
	public void setNights(final Integer nights) {
		this.nights = nights;
	}

	@Override
	public String getImage() {
		return image;
	}

	@Override
	public void setImage(final String image) {
		this.image = image;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public ICity getCity() {
		return city;
	}

	@Override
	public void setCity(final ICity city) {
		this.city = city;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(final String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Tour [tourStatus=" + tourStatus + ", name=" + name + ", price=" + price + ", tourType="
				+ tourType.getId() + ", nights=" + nights + ", image=" + image + ", description=" + description
				+ ", city=" + city.getId() + ", address=" + address + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

}
