package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ITaDescriptionDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.service.ITaDescriptionService;

@Service
public class TaDescriptionServiceImpl implements ITaDescriptionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaDescriptionServiceImpl.class);

	private ITaDescriptionDao dao;

	@Autowired
	public TaDescriptionServiceImpl(final ITaDescriptionDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ITaDescription createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final ITaDescription entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New TA description created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("TA description updated: {}", entity);
		}
	}

	@Override
	public ITaDescription getById(final Integer id) {
		final ITaDescription entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ITaDescription> getAll() {
		final List<ITaDescription> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("TA description id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All TA description was deleted.");
	}

}
