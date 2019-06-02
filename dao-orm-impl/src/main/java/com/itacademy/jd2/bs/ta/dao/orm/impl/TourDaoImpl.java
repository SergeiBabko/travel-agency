package com.itacademy.jd2.bs.ta.dao.orm.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITourDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.City_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Country_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Tour;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.TourType_;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Tour_;

@Repository
public class TourDaoImpl extends AbstractDaoImpl<ITour, Integer> implements ITourDao {

	protected TourDaoImpl() {
		super(Tour.class);
	}

	@Override
	public ITour createEntity() {
		final Tour tour = new Tour();
		return tour;
	}

	@Override
	public List<ITour> find(final TourFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ITour> cq = cb.createQuery(ITour.class);
		final Root<Tour> from = cq.from(Tour.class);
		cq.select(from);

		from.fetch(Tour_.city, JoinType.LEFT).fetch(City_.country, JoinType.LEFT);
		from.fetch(Tour_.tourType, JoinType.LEFT);

		applyFilter(filter, cb, cq, from);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<ITour> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public ITour getById(final Integer id) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ITour> cq = cb.createQuery(ITour.class);
		final Root<Tour> from = cq.from(Tour.class);
		cq.select(from);

		from.fetch(Tour_.city, JoinType.LEFT).fetch(City_.country, JoinType.LEFT);
		from.fetch(Tour_.tourType, JoinType.LEFT);

		cq.where(cb.equal(from.get(Tour_.id), id));

		final TypedQuery<ITour> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	private void applyFilter(final TourFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
			final Root<Tour> from) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		final Integer countryId = filter.getCountryId();
		final Integer cityId = filter.getCityId();
		final Integer tourTypeId = filter.getTourTypeId();
		final Integer nightsMin = filter.getNightsMin();
		final Integer nightsMax = filter.getNightsMax();
		final Integer priceMin = filter.getPriceMin();
		final Integer priceMax = filter.getPriceMax();

		boolean allNightsChanged = false;
		boolean allPriceChanged = false;

		if (nightsMin != null && nightsMax != null) {
			allNightsChanged = nightsMin != 0 || nightsMax != getMaxNights();
		}

		if (priceMin != null && priceMax != null) {
			allPriceChanged = priceMin != 0 || priceMax != getMaxPrice();
		}

		if (countryId != null && cityId == null) {
			predicates.add(cb.equal(from.get(Tour_.city).get(City_.country).get(Country_.id), countryId));
		}

		if (cityId != null) {
			predicates.add(cb.equal(from.get(Tour_.city).get(City_.id), cityId));
		}

		if (tourTypeId != null) {
			predicates.add(cb.equal(from.get(Tour_.tourType).get(TourType_.id), tourTypeId));
		}

		if (allNightsChanged) {
			predicates.add(cb.between(from.get(Tour_.nights), nightsMin, nightsMax));
		}

		if (allPriceChanged) {
			predicates.add(cb.between(from.get(Tour_.price), priceMin, priceMax));
		}

		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}

	}

	@Override
	public Integer getMaxPrice() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
		final Root<Tour> from = cq.from(Tour.class);

		cq.select(cb.max(from.get(Tour_.price)));

		Integer result = em.createQuery(cq).getSingleResult();

		return result;
	}

	@Override
	public Integer getMaxNights() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
		final Root<Tour> from = cq.from(Tour.class);

		cq.select(cb.max(from.get(Tour_.nights)));

		Integer result = em.createQuery(cq).getSingleResult();

		return result;
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Tour> from = cq.from(Tour.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public void delete(final Integer id) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ITour> cq = cb.createQuery(ITour.class);
		final Root<Tour> from = cq.from(Tour.class);
		cq.select(from);
		cq.where(cb.equal(from.get(Tour_.id), id));

		final TypedQuery<ITour> q = em.createQuery(cq);

		ITour entity = q.getSingleResult();

		if (entity.getTourStatus().equals(TourStatus.active)) {
			entity.setTourStatus(TourStatus.inactive);
		} else {
			entity.setTourStatus(TourStatus.active);
		}

		update(entity);
	}

	private Path<?> getSortPath(final Root<Tour> from, final String sortColumn) {
		switch (sortColumn) {
		case "id":
			return from.get(Tour_.id);
		case "created":
			return from.get(Tour_.created);
		case "updated":
			return from.get(Tour_.updated);

		case "name":
			return from.get(Tour_.name);
		case "city":
			return from.get(Tour_.city).get(City_.name);
		case "country":
			return from.get(Tour_.city).get(City_.country).get(Country_.name);
		case "nights":
			return from.get(Tour_.nights);
		case "price":
			return from.get(Tour_.price);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}

}
