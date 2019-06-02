package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;

public interface IReviewService {

	IReview createEntity();

	IReview getById(Integer id);

	List<IReview> getAll();

	@Transactional
	void save(IReview entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<IReview> find(ReviewFilter filter);

	long getCount();

	List<IReview> getByCustomerId(ReviewFilter filter, Integer customerId);

	List<IReview> getByTourId(ReviewFilter filter, Integer tourId);

}