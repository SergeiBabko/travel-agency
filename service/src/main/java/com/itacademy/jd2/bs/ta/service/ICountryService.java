package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.filter.CountryFilter;

public interface ICountryService {

	ICountry createEntity();

	ICountry getById(Integer id);

	List<ICountry> getAll();

	@Transactional
	void save(ICountry entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<ICountry> find(CountryFilter filter);

	long getCount();
}
