package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.IBookedDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;
import com.itacademy.jd2.bs.ta.service.IBookedService;

@Service
public class BookedServiceImpl implements IBookedService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookedServiceImpl.class);

	private IBookedDao dao;

	@Autowired
	public BookedServiceImpl(final IBookedDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public IBooked createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final IBooked entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insert(entity);
			LOGGER.info("New booked created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("Booked updated: {}", entity);
		}
	}

	@Override
	public IBooked getById(final Integer id) {
		final IBooked entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<IBooked> getAll() {
		final List<IBooked> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Booked id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All booked was deleted.");
	}

	@Override
	public List<IBooked> find(final BookedFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

	@Override
	public List<IBooked> getByCustomerId(final BookedFilter filter, final Integer customerId) {
		return dao.getByCustomerId(filter, customerId);
	}

	@Override
	public long getCountByTourId(final Integer tourId) {
		return dao.getCountByTourId(tourId);
	}

	@Override
	public Map<Integer, Long> getBookedByMonth() {
		return dao.getBookedByMonth();
	}

}
