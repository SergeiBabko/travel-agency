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

import com.itacademy.jd2.bs.ta.dao.api.ICustomerDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Customer;
import com.itacademy.jd2.bs.ta.dao.orm.impl.entity.Customer_;

@Repository
public class CustomerDaoImpl extends AbstractDaoImpl<ICustomer, Integer> implements ICustomerDao {

	protected CustomerDaoImpl() {
		super(Customer.class);
	}

	@Override
	public ICustomer createEntity() {
		final Customer customer = new Customer();
		return customer;
	}

	@Override
	public List<ICustomer> find(final CustomerFilter filter) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ICustomer> cq = cb.createQuery(ICustomer.class);
		final Root<Customer> from = cq.from(Customer.class);
		cq.select(from);

		from.fetch(Customer_.userAccount, JoinType.LEFT);

		final String sortColumn = filter.getSortColumn();
		if (sortColumn != null) {
			final Path<?> expression = getSortPath(from, sortColumn);
			cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
		}

		final TypedQuery<ICustomer> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

	@Override
	public ICustomer getById(final Integer id) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ICustomer> cq = cb.createQuery(ICustomer.class);
		final Root<Customer> from = cq.from(Customer.class);
		cq.select(from);

		from.fetch(Customer_.userAccount, JoinType.LEFT);

		cq.where(cb.equal(from.get(Customer_.id), id));

		final TypedQuery<ICustomer> q = em.createQuery(cq);

		return q.getSingleResult();
	}

	@Override
	public long getCount() {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<Customer> from = cq.from(Customer.class);
		cq.select(cb.count(from));
		final TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public void delete(final Integer id) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ICustomer> cq = cb.createQuery(ICustomer.class);
		final Root<Customer> from = cq.from(Customer.class);
		cq.select(from);
		cq.where(cb.equal(from.get(Customer_.id), id));

		final TypedQuery<ICustomer> q = em.createQuery(cq);

		ICustomer entity = q.getSingleResult();

		if (entity.getStatus().equals(CustomerStatus.active)) {
			entity.setStatus(CustomerStatus.deleted);
		} else {
			entity.setStatus(CustomerStatus.active);
		}

		update(entity);
	}

	@Override
	public String activateOrDeactivate(final Integer customerId) {
		final EntityManager em = getEntityManager();
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<ICustomer> cq = cb.createQuery(ICustomer.class);
		final Root<Customer> from = cq.from(Customer.class);
		cq.select(from);
		cq.where(cb.equal(from.get(Customer_.id), customerId));

		final TypedQuery<ICustomer> q = em.createQuery(cq);

		ICustomer entity = q.getSingleResult();

		if (entity.getStatus().equals(CustomerStatus.active)) {
			entity.setStatus(CustomerStatus.blocked);
		} else {
			entity.setStatus(CustomerStatus.active);
		}

		update(entity);
		return entity.getStatus().name();
	}

	private Path<?> getSortPath(final Root<Customer> from, final String sortColumn) {
		switch (sortColumn) {
		case "id":
			return from.get(Customer_.id);
		case "created":
			return from.get(Customer_.created);
		case "updated":
			return from.get(Customer_.updated);

		case "status":
			return from.get(Customer_.status);
		default:
			throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
		}
	}

}
