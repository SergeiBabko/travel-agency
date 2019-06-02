package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;

public interface IFavoriteDao extends IDao<IFavorite, Integer> {

	List<IFavorite> find(FavoriteFilter filter);

	long getCount();

	long getCountByTourId(Integer tourId);

	List<IFavorite> getByCustomerId(Integer id, FavoriteFilter filter);

}
