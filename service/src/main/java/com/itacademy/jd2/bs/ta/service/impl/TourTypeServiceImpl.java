package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ITourTypeDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;

@Service
public class TourTypeServiceImpl implements ITourTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourTypeServiceImpl.class);

	private ITourTypeDao dao;

	@Autowired
	public TourTypeServiceImpl(final ITourTypeDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ITourType createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final ITourType entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New tour type created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Tour type updated: {}", entity);
		}
	}

	@Override
	public ITourType getById(final Integer id) {
		final ITourType entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ITourType> getAll() {
		final List<ITourType> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Tour type id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All tour types was deleted.");
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

}
