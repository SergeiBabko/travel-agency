package com.itacademy.jd2.bs.ta.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;

public interface IUserAccountService {

	IUserAccount createEntity();

	IUserAccount getById(Integer id);

	IUserAccount getByEmail(String email);

	List<IUserAccount> getAll();

	@Transactional
	void save(IUserAccount entity) throws NotUniqueUserException;

	@Transactional
	void delete(Integer id);

	@Transactional
	void deleteAll();

	List<IUserAccount> find(UserAccountFilter filter);

	long getCount();

	boolean validateEmail(String email);

}