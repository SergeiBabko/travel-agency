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

import com.itacademy.jd2.bs.ta.dao.api.IFavoriteDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Customer_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Favorite;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Favorite_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Tour_;

@Repository
public class FavoriteDaoImpl extends AbstractDaoImpl<IFavorite, Integer> implements IFavoriteDao {

	protected FavoriteDaoImpl() {
		super(Favorite.class);
	}

	@Override
	public IFavorite createEntity() {
		final Favorite favorite = new Favorite();
		return favorite;
	}

	@Override
	public List<IFavorite> find(final FavoriteFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IFavorite> cq = cb.createQuery(IFavorite.class);
		final Root<Favorite> from = cq.from(Favorite.class);
		cq.select(from);

		from.fetch(Favorite_.customer, JoinType.LEFT);
		from.fetch(Favorite_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IFavorite> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public List<IFavorite> getByCustomerId(final Integer id, final FavoriteFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<IFavorite> cq = cb.createQuery(IFavorite.class);
		final Root<Favorite> from = cq.from(Favorite.class);
		cq.select(from);

		cq.where(cb.equal(from.get(Favorite_.customer).get(Customer_.id), id));

		from.fetch(Favorite_.customer, JoinType.LEFT);
		from.fetch(Favorite_.tour, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<IFavorite> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Favorite> from = cq.from(Favorite.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	private Path<?> getSortPath(final Root<Favorite> from, final String sortColumn) {
		switch (sortColumn) {
		case "id":
			return from.get(Favorite_.id);
		case "created":
			return from.get(Favorite_.created);
		case "updated":
			return from.get(Favorite_.updated);

		case "tourName":
			return from.get(Favorite_.tour).get(Tour_.name);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}

	@Override
	public long getCountByTourId(final Integer tourId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Favorite> from = cq.from(Favorite.class);
		cq.select(cb.count(from));
		cq.where(cb.equal(from.get(Favorite_.tour).get(Tour_.id), tourId));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

}
