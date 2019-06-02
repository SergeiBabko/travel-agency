package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ITourDateDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;
import com.itacademy.jd2.bs.ta.service.ITourDateService;

@Service
public class TourDateServiceImpl implements ITourDateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourDateServiceImpl.class);

	private ITourDateDao dao;

	@Autowired
	public TourDateServiceImpl(final ITourDateDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ITourDate createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final ITourDate entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New tour date created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Tour date updated: {}", entity);
		}
	}

	@Override
	public ITourDate getById(final Integer id) {
		final ITourDate entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ITourDate> getAll() {
		final List<ITourDate> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Tour date id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All tour dates was deleted.");
	}

	@Override
	public List<ITourDate> getByTourId(final TourDateFilter filter, final Integer tourId) {
		final List<ITourDate> byTourId = dao.getByTourId(filter, tourId);
		return byTourId;
	}

}
