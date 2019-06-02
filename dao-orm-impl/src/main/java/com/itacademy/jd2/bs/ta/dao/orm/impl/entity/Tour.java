package com.itacademy.jd2.bs.ta.dao.orm.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;

@Entity
public class Tour extends BaseEntity implements ITour {

	@Column
	@Version
	private Integer version;

	@Column
	@Enumerated(EnumType.STRING)
	private TourStatus tourStatus;

	@Column
	private String name;

	@Column
	private Integer price;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = TourType.class)
	private ITourType tourType;

	@Column
	private Integer nights;

	@Column
	private String image;

	@Column
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = City.class)
	private ICity city;

	@Column
	private String address;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Tour [tourStatus=" + tourStatus + ", name=" + name + ", price=" + price + ", tourType="
				+ tourType.getId() + ", nights=" + nights + ", image=" + image + ", description=" + description
				+ ", city=" + city.getId() + ", address=" + address + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + ", getUpdated()=" + getUpdated() + "]";
	}

}
