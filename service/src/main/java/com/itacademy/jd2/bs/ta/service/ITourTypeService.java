package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;

public interface ITourTypeService {

	ITourType createEntity();

	ITourType getById(Integer id);

	List<ITourType> getAll();

	@Transactional
	void save(ITourType entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	long getCount();

}