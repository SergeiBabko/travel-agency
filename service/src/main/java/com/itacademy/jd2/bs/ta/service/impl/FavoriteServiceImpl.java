package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.IFavoriteDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;

@Service
public class FavoriteServiceImpl implements IFavoriteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteServiceImpl.class);

	private IFavoriteDao dao;

	@Autowired
	public FavoriteServiceImpl(final IFavoriteDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public IFavorite createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final IFavorite entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New favorite created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Favorite updated: {}", entity);
		}
	}

	@Override
	public IFavorite getById(final Integer id) {
		final IFavorite entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<IFavorite> getAll() {
		final List<IFavorite> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Favorite id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All favorites was deleted.");
	}

	@Override
	public List<IFavorite> find(final FavoriteFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

	@Override
	public List<IFavorite> getByCustomerId(final Integer id, final FavoriteFilter filter) {
		return dao.getByCustomerId(id, filter);
	}

	@Override
	public long getCountByTourId(final Integer tourId) {
		return dao.getCountByTourId(tourId);
	}

}
