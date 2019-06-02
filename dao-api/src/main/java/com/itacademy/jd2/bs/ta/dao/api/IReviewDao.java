package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;

public interface IReviewDao extends IDao<IReview, Integer> {

	List<IReview> find(ReviewFilter filter);

	long getCount();

	List<IReview> getByCustomerId(ReviewFilter filter, Integer customerId);

	List<IReview> getByTourId(ReviewFilter filter, Integer tourId);

}
