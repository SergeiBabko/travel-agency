package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ITourDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;
import com.itacademy.jd2.bs.ta.service.ITourService;

@Service
public class TourServiceImpl implements ITourService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);

	private ITourDao dao;

	@Autowired
	public TourServiceImpl(final ITourDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ITour createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final ITour entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New tour created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Tour updated: {}", entity);
		}
	}

	@Override
	public ITour getById(final Integer id) {
		final ITour entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ITour> getAll() {
		final List<ITour> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Tour id={} set as {}.", id, getById(id).getTourStatus());
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All tours was deleted.");
	}

	@Override
	public List<ITour> find(final TourFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

	@Override
	public Integer getMaxPrice() {
		return dao.getMaxPrice();
	}

	@Override
	public Integer getMaxNights() {
		return dao.getMaxNights();
	}

}
