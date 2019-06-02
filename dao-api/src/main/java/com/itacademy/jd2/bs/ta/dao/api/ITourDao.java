package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;

public interface ITourDao extends IDao<ITour, Integer> {

	List<ITour> find(TourFilter filter);

	long getCount();

	Integer getMaxPrice();

	Integer getMaxNights();

}