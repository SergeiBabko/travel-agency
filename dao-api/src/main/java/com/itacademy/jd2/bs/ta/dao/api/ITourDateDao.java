package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;

public interface ITourDateDao extends IDao<ITourDate, Integer> {

	List<ITourDate> getByTourId(TourDateFilter filter, Integer tourId);

}