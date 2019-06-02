package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;

public interface ITourService {

	ITour createEntity();

	ITour getById(Integer id);

	List<ITour> getAll();

	@Transactional
	void save(ITour entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<ITour> find(TourFilter filter);

	long getCount();

	Integer getMaxPrice();

	Integer getMaxNights();

}