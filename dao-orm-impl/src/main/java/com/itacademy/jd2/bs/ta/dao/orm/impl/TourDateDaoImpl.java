package com.itacademy.jd2.bs.ta.dao.orm.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITourDateDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TourDate;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TourDate_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Tour_;

@Repository
public class TourDateDaoImpl extends AbstractDaoImpl<ITourDate, Integer> implements ITourDateDao {

	protected TourDateDaoImpl() {
		super(TourDate.class);
	}

	@Override
	public ITourDate createEntity() {
		final TourDate tourDate = new TourDate();
		return tourDate;
	}

	@Override
	public List<ITourDate> getByTourId(final TourDateFilter filter, final Integer tourId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ITourDate> cq = cb.createQuery(ITourDate.class);
		final Root<TourDate> from = cq.from(TourDate.class);
		cq.select(from);

		from.fetch(TourDate_.tour, JoinType.LEFT);

		cq.where(cb.equal(from.get(TourDate_.tour).get(Tour_.id), tourId));

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<ITourDate> q = em.createQuery(cq);

		return q.getResultList();
	}

	private Path<?> getSortPath(final Root<TourDate> from, final String sortColumn) {
		switch (sortColumn) {
		case "dateStart":
			return from.get(TourDate_.dateStart);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}

}
