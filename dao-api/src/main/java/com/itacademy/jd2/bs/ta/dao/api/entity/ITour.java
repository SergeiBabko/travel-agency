package com.itacademy.jd2.bs.ta.dao.api.entity;

import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;

public interface ITour extends IBaseEntity {

	TourStatus getTourStatus();

	void setTourStatus(TourStatus tourStatus);

	String getName();

	void setName(String name);

	Integer getPrice();

	void setPrice(Integer price);

	ITourType getTourType();

	void setTourType(ITourType tourType);

	Integer getNights();

	void setNights(Integer nights);

	String getImage();

	void setImage(String image);

	String getDescription();

	void setDescription(String description);

	ICity getCity();

	void setCity(ICity city);

	String getAddress();

	void setAddress(String address);

	Integer getVersion();

	void setVersion(Integer version);

}
