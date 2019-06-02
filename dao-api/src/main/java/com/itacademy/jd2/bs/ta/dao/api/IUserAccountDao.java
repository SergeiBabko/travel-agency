package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;

public interface IUserAccountDao extends IDao<IUserAccount, Integer> {

	List<IUserAccount> find(UserAccountFilter filter);

	long getCount();

	IUserAccount getByEmail(String email);

	void insertUserAccount(IUserAccount userAccount) throws NotUniqueUserException;

}