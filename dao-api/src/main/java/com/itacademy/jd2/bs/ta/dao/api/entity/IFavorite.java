package com.itacademy.jd2.bs.ta.dao.api.entity;

public interface IFavorite extends IBaseEntity {

	ICustomer getCustomer();

	void setCustomer(ICustomer customer);

	ITour getTour();

	void setTour(ITour tour);

}
