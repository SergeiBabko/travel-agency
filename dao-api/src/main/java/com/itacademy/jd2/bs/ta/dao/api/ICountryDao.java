package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.filter.CountryFilter;

public interface ICountryDao extends IDao<ICountry, Integer> {

	List<ICountry> find(CountryFilter filter);

	long getCount();

}