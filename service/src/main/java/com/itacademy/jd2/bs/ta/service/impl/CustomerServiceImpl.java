package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.ICustomerDao;
import com.itacademy.jd2.bs.ta.dao.api.IUserAccountDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private ICustomerDao dao;
	private IUserAccountDao userAccountDao;

	@Autowired
	public CustomerServiceImpl(final ICustomerDao dao, final IUserAccountDao userAccountDao) {
		super();
		this.dao = dao;
		this.userAccountDao = userAccountDao;
	}

	@Override
	public ICustomer createEntity() {
		return dao.createEntity();
	}

	@Override
	public IUserAccount createUserAccountEntity() {
		return userAccountDao.createEntity();
	}

	@Override
	public void save(final IUserAccount userAccount, final ICustomer entity) throws NotUniqueUserException {
		final Date modifedOn = new Date();
		userAccount.setUpdated(modifedOn);
		userAccount.setCreated(modifedOn);
		userAccount.setRole(UserRole.user);
		entity.setUpdated(modifedOn);
		entity.setCreated(modifedOn);

		userAccountDao.insertUserAccount(userAccount);

		entity.setId(userAccount.getId());
		entity.setUserAccount(userAccount);
		entity.setStatus(CustomerStatus.active);

		dao.insert(entity);
		userAccount.setCustomer(entity);

		LOGGER.info("New user_account and customer created: {}", entity);
	}

	@Override
	public void update(final ICustomer entity) {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		dao.update(entity);
		LOGGER.debug("Customer updated: {}", entity);
	}

	@Override
	public ICustomer getById(final Integer id) {
		final ICustomer entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<ICustomer> getAll() {
		final List<ICustomer> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("Customer id={} set as {}.", id, getById(id).getStatus());
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All customers was deleted.");
	}

	@Override
	public List<ICustomer> find(final CustomerFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

	@Override
	public String activateOrDeactivate(final Integer customerId) {
		return dao.activateOrDeactivate(customerId);
	}

}
