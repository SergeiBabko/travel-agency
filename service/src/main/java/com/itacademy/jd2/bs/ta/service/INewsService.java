package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.filter.NewsFilter;

public interface INewsService {

	INews createEntity();

	INews getById(Integer id);

	List<INews> getAll();

	@Transactional
	void save(INews entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<INews> find(NewsFilter filter);

	long getCount();

}