package com.itacademy.jd2.bs.ta.dao.orm.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITourTypeDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TourType;

@Repository
public class TourTypeDaoImpl extends AbstractDaoImpl<ITourType, Integer> implements ITourTypeDao {

	protected TourTypeDaoImpl() {
		super(TourType.class);
	}

	@Override
	public ITourType createEntity() {
		final TourType tourType = new TourType();
		return tourType;
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<TourType> from = cq.from(TourType.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

}
