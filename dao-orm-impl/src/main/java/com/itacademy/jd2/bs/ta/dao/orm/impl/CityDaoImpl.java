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

import com.itacademy.jd2.bs.ta.dao.api.ICityDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.City;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.City_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Country_;

@Repository
public class CityDaoImpl extends AbstractDaoImpl<ICity, Integer> implements ICityDao {

	protected CityDaoImpl() {
		super(City.class);
	}

	@Override
	public ICity createEntity() {
		final City city = new City();
		return city;
	}

	@Override
	public List<ICity> find(final CityFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ICity> cq = cb.createQuery(ICity.class);
		final Root<City> from = cq.from(City.class);
		cq.select(from);

		from.fetch(City_.country, JoinType.LEFT);

		final String sortColumn1 = filter.getSortColumn2();
		final String sortColumn2 = filter.getSortColumn();

		if (sortColumn1 != null && sortColumn2 != null) {
			final Path<?> expression1 = getSortPath(from, sortColumn1);
			final Path<?> expression2 = getSortPath(from, sortColumn2);
			cq.orderBy(new OrderImpl(expression1, filter.getSortOrder()),
					new OrderImpl(expression2, filter.getSortOrder()));
		}

		if (sortColumn1 != null && sortColumn2 == null) {
			final Path<?> expression1 = getSortPath(from, sortColumn1);
			cq.orderBy(new OrderImpl(expression1, filter.getSortOrder()));
		}

		final TypedQuery<ICity> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public ICity getById(final Integer id) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ICity> cq = cb.createQuery(ICity.class);
		final Root<City> from = cq.from(City.class);
		cq.select(from);

		from.fetch(City_.country, JoinType.LEFT);

		cq.where(cb.equal(from.get(City_.id), id));

		final TypedQuery<ICity> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<City> from = cq.from(City.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	private Path<?> getSortPath(final Root<City> from, final String sortColumn) {
		switch (sortColumn) {
		case "name":
			return from.get(City_.name);
		case "country":
			return from.get(City_.country).get(Country_.name);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}

}
