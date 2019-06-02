package com.itacademy.jd2.bs.ta.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;

public interface IBookedService {

	IBooked createEntity();

	IBooked getById(Integer id);

	List<IBooked> getByCustomerId(BookedFilter filter, Integer customerId);

	List<IBooked> getAll();

	@Transactional
	void save(IBooked entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<IBooked> find(BookedFilter filter);

	long getCount();

	long getCountByTourId(Integer tourId);

	Map<Integer, Long> getBookedByMonth();

}