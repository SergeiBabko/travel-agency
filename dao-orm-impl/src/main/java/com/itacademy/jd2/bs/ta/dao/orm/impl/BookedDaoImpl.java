package com.itacademy.jd2.bs.ta.dao.orm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.IBookedDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Booked;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Booked_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Customer_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TourDate_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Tour_;

@Repository
public class BookedDaoImpl extends AbstractDaoImpl<IBooked, Integer> implements IBookedDao {

	protected BookedDaoImpl() {
		super(Booked.class);
	}

	@Override
	public IBooked createEntity() {
		final Booked booked = new Booked();
		return booked;
	}

	@Override
	public List<IBooked> find(final BookedFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IBooked> cq = cb.createQuery(IBooked.class);
		final Root<Booked> from = cq.from(Booked.class);
		cq.select(from);

		from.fetch(Booked_.customer, JoinType.LEFT);
		from.fetch(Booked_.tourDate, JoinType.LEFT).fetch(TourDate_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IBooked> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public IBooked getById(final Integer id) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IBooked> cq = cb.createQuery(IBooked.class);
		final Root<Booked> from = cq.from(Booked.class);
		cq.select(from);

		cq.where(cb.equal(from.get(Booked_.id), id));

		from.fetch(Booked_.customer, JoinType.LEFT);
		from.fetch(Booked_.tourDate, JoinType.LEFT).fetch(TourDate_.tour, JoinType.LEFT);

		final TypedQuery<IBooked> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<IBooked> getByCustomerId(final BookedFilter filter, final Integer customerId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IBooked> cq = cb.createQuery(IBooked.class);
		final Root<Booked> from = cq.from(Booked.class);
		cq.select(from);

		cq.where(cb.equal(from.get(Booked_.customer).get(Customer_.id), customerId));

		from.fetch(Booked_.customer, JoinType.LEFT);
		from.fetch(Booked_.tourDate, JoinType.LEFT).fetch(TourDate_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IBooked> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Booked> from = cq.from(Booked.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public long getCountByTourId(final Integer tourId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Booked> from = cq.from(Booked.class);
		cq.select(cb.count(from));
		cq.where(cb.equal(from.get(Booked_.tourDate).get(TourDate_.tour).get(Tour_.id), tourId));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public Map<Integer, Long> getBookedByMonth() {
		final Map<Integer, Long> result = new HashMap<>();
		for (int i = 1; i <= 12; i++) {
			TypedQuery<Long> query = getEntityManager().createQuery(
					"select count(*) from com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Booked where EXTRACT(MONTH FROM created) = "
							+ i,
					Long.class);
			Long count = query.getSingleResult();
			result.put(i, count);
		}
		return result;
	}

	private Path<?> getSortPath(final Root<Booked> from, final String sortColumn) {
		switch (sortColumn) {
		case "id":
			return from.get(Booked_.id);
		case "created":
			return from.get(Booked_.created);
		case "updated":
			return from.get(Booked_.updated);

		case "processed":
			return from.get(Booked_.processed);
		case "price":
			return from.get(Booked_.price);
		case "tourDate":
			return from.get(Booked_.tourDate).get(TourDate_.dateStart);
		case "tourName":
			return from.get(Booked_.tourDate).get(TourDate_.tour).get(Tour_.name);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}

}
