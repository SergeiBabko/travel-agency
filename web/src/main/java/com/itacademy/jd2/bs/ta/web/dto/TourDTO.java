package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;

public class TourDTO {

	private Integer id;
	private Date created;
	private Date updated;
	private Integer version;

	@NotNull
	private TourStatus tourStatus;
	@NotEmpty
	private String name;
	@NotNull
	private Integer price;
	@NotNull
	private Integer tourTypeId;
	@NotNull
	private Integer nights;
	@NotEmpty
	private String image;
	@NotEmpty
	private String description;
	@NotNull
	private Integer cityId;
	@NotEmpty
	private String address;

	private String tourTypeName;
	private String cityName;
	private String countryName;
	private Integer countryId;
	private boolean favorite;
	private boolean isNew;

	private long favoriteCount;
	private long bookedCount;

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

	public TourStatus getTourStatus() {
		return tourStatus;
	}

	public void setTourStatus(final TourStatus tourStatus) {
		this.tourStatus = tourStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(final Integer price) {
		this.price = price;
	}

	public Integer getTourTypeId() {
		return tourTypeId;
	}

	public void setTourTypeId(final Integer tourTypeId) {
		this.tourTypeId = tourTypeId;
	}

	public Integer getNights() {
		return nights;
	}

	public void setNights(final Integer nights) {
		this.nights = nights;
	}

	public String getImage() {
		return image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(final Integer cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getTourTypeName() {
		return tourTypeName;
	}

	public void setTourTypeName(final String tourTypeName) {
		this.tourTypeName = tourTypeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(final Integer countryId) {
		this.countryId = countryId;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(final boolean favorite) {
		this.favorite = favorite;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setNew(final boolean isNew) {
		this.isNew = isNew;
	}

	public long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(final long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public long getBookedCount() {
		return bookedCount;
	}

	public void setBookedCount(final long bookedCount) {
		this.bookedCount = bookedCount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

}
