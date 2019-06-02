package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.IReviewDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;
import com.itacademy.jd2.bs.ta.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

	private IReviewDao dao;

	@Autowired
	public ReviewServiceImpl(final IReviewDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public IReview createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final IReview entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New review created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Review updated: {}", entity);
		}
	}

	@Override
	public IReview getById(final Integer id) {
		final IReview entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<IReview> getAll() {
		final List<IReview> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Review id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All review was deleted.");
	}

	@Override
	public List<IReview> find(final ReviewFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

	@Override
	public List<IReview> getByCustomerId(final ReviewFilter filter, final Integer customerId) {
		return dao.getByCustomerId(filter, customerId);
	}

	@Override
	public List<IReview> getByTourId(final ReviewFilter filter, final Integer tourId) {
		return dao.getByTourId(filter, tourId);
	}

}
