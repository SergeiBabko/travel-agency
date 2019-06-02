package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ICountryDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.filter.CountryFilter;
import com.itacademy.jd2.bs.ta.service.ICountryService;

@Service
public class CountryServiceImpl implements ICountryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

	private ICountryDao dao;

	@Autowired
	public CountryServiceImpl(final ICountryDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ICountry createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final ICountry entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New country created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Country updated: {}", entity);
		}
	}

	@Override
	public ICountry getById(final Integer id) {
		final ICountry entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ICountry> getAll() {
		final List<ICountry> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Country id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All countries was deleted.");
	}

	@Override
	public List<ICountry> find(final CountryFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

}
