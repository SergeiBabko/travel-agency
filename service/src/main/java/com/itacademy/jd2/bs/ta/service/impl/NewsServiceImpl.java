package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.INewsDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.filter.NewsFilter;
import com.itacademy.jd2.bs.ta.service.INewsService;

@Service
public class NewsServiceImpl implements INewsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewsServiceImpl.class);

	private INewsDao dao;

	@Autowired
	public NewsServiceImpl(final INewsDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public INews createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final INews entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New news created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("News updated: {}", entity);
		}
	}

	@Override
	public INews getById(final Integer id) {
		final INews entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<INews> getAll() {
		final List<INews> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("News id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All news was deleted.");
	}

	@Override
	public List<INews> find(final NewsFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

}
