package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;

public interface ICityDao extends IDao<ICity, Integer> {

	List<ICity> find(CityFilter filter);

	long getCount();

}
