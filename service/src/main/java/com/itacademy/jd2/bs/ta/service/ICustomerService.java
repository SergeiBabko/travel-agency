package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;

public interface ICustomerService {

	ICustomer createEntity();

	IUserAccount createUserAccountEntity();

	ICustomer getById(Integer id);

	List<ICustomer> getAll();

	@Transactional
	void save(IUserAccount userAccount, ICustomer entity) throws NotUniqueUserException;

	@Transactional
	void update(ICustomer entity);

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	@Transactional
	String activateOrDeactivate(Integer customerId);

	List<ICustomer> find(CustomerFilter filter);

	long getCount();

}