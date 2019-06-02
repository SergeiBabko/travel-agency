package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;

public interface IFavoriteService {

	IFavorite createEntity();

	IFavorite getById(Integer id);

	List<IFavorite> getAll();

	@Transactional
	void save(IFavorite entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<IFavorite> find(FavoriteFilter filter);

	long getCount();

	long getCountByTourId(Integer tourId);

	List<IFavorite> getByCustomerId(Integer id, FavoriteFilter filter);

}