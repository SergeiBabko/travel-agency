package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;

public interface ITourDateService {

	ITourDate createEntity();

	ITourDate getById(Integer id);

	List<ITourDate> getAll();

	List<ITourDate> getByTourId(TourDateFilter filter, Integer tourId);

	@Transactional
	void save(ITourDate entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

}