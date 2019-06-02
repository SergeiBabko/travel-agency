package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;
import java.util.Map;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;

public interface IBookedDao extends IDao<IBooked, Integer> {

	List<IBooked> find(BookedFilter filter);

	long getCount();
	
	long getCountByTourId(Integer tourId);

	List<IBooked> getByCustomerId(BookedFilter filter, Integer customerId);

	Map<Integer, Long> getBookedByMonth();

}
