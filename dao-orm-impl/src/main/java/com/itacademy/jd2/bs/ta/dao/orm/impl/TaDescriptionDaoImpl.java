package com.itacademy.jd2.bs.ta.dao.orm.impl;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITaDescriptionDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TaDescription;

@Repository
public class TaDescriptionDaoImpl extends AbstractDaoImpl<ITaDescription, Integer> implements ITaDescriptionDao {

	protected TaDescriptionDaoImpl() {
		super(TaDescription.class);
	}

	@Override
	public ITaDescription createEntity() {
		final TaDescription taDescription = new TaDescription();
		return taDescription;
	}

}
