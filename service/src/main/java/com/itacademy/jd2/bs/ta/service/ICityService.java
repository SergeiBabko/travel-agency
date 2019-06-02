package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;

public interface ICityService {

	ICity createEntity();

	ICity getById(Integer id);

	List<ICity> getAll();

	@Transactional
	void save(ICity entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<ICity> find(CityFilter filter);

	long getCount();

}