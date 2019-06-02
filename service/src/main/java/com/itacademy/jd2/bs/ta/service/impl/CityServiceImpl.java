package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ICityDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;
import com.itacademy.jd2.bs.ta.service.ICityService;

@Service
public class CityServiceImpl implements ICityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

	private ICityDao dao;

	@Autowired
	public CityServiceImpl(final ICityDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ICity createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final ICity entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New city created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("City updated: {}", entity);
		}
	}

	@Override
	public ICity getById(final Integer id) {
		final ICity entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ICity> getAll() {
		final List<ICity> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("City id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All cities was deleted.");
	}

	@Override
	public List<ICity> find(final CityFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

}
