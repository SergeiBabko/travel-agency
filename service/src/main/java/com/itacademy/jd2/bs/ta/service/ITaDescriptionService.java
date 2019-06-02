package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;

public interface ITaDescriptionService {

	ITaDescription createEntity();

	ITaDescription getById(Integer id);

	List<ITaDescription> getAll();

	@Transactional
	void save(ITaDescription entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

}