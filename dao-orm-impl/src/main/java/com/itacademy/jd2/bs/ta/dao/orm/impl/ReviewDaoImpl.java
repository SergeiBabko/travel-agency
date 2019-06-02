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

import com.itacademy.jd2.bs.ta.dao.api.IReviewDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Customer_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Review;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Review_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TourDate_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Tour_;

@Repository
public class ReviewDaoImpl extends AbstractDaoImpl<IReview, Integer> implements IReviewDao {

	protected ReviewDaoImpl() {
		super(Review.class);
	}

	@Override
	public IReview createEntity() {
		final Review review = new Review();
		return review;
	}

	@Override
	public List<IReview> find(final ReviewFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IReview> cq = cb.createQuery(IReview.class);
		final Root<Review> from = cq.from(Review.class);
		cq.select(from);

		from.fetch(Review_.customer, JoinType.LEFT).fetch(Customer_.userAccount, JoinType.LEFT);
		from.fetch(Review_.tourDate, JoinType.LEFT).fetch(TourDate_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IReview> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public List<IReview> getByCustomerId(final ReviewFilter filter, final Integer customerId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IReview> cq = cb.createQuery(IReview.class);
		final Root<Review> from = cq.from(Review.class);
		cq.select(from);

		cq.where(cb.equal(from.get(Review_.customer).get(Customer_.id), customerId));

		from.fetch(Review_.customer, JoinType.LEFT).fetch(Customer_.userAccount, JoinType.LEFT);
		from.fetch(Review_.tourDate, JoinType.LEFT).fetch(TourDate_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IReview> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public List<IReview> getByTourId(final ReviewFilter filter, final  Integer tourId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IReview> cq = cb.createQuery(IReview.class);
		final Root<Review> from = cq.from(Review.class);
		cq.select(from);

		cq.where(cb.equal(from.get(Review_.tourDate).get(TourDate_.tour).get(Tour_.id), tourId));

		from.fetch(Review_.customer, JoinType.LEFT).fetch(Customer_.userAccount, JoinType.LEFT);
		from.fetch(Review_.tourDate, JoinType.LEFT).fetch(TourDate_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IReview> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Review> from = cq.from(Review.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	private Path<?> getSortPath(final Root<Review> from, final String sortColumn) {
		switch (sortColumn) {
		case "id":
			return from.get(Review_.id);
		case "created":
			return from.get(Review_.created);
		case "updated":
			return from.get(Review_.updated);

		case "rating":
			return from.get(Review_.rating);
		case "tourDate":
			return from.get(Review_.tourDate).get(TourDate_.dateStart);
		case "tourName":
			return from.get(Review_.tourDate).get(TourDate_.tour).get(Tour_.name);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}
}
