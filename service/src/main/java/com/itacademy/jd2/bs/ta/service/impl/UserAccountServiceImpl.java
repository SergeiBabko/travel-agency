package com.itacademy.jd2.bs.ta.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.IUserAccountDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);

	private IUserAccountDao dao;

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

	private static Pattern pattern;
	private Matcher matcher;

	@Autowired
	public UserAccountServiceImpl(final IUserAccountDao dao) {
		super();
		this.dao = dao;
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public IUserAccount createEntity() {
		return dao.createEntity();
	}

	@Override
	public void save(final IUserAccount entity) throws NotUniqueUserException {
		final Date modifedOn = new Date();
		entity.setUpdated(modifedOn);
		if (entity.getId() == null) {
			entity.setCreated(modifedOn);
			dao.insertUserAccount(entity);
			LOGGER.info("New user account created: {}", entity);
		} else {
			dao.update(entity);
			LOGGER.debug("User account updated: {}", entity);
		}
	}

	@Override
	public IUserAccount getById(final Integer id) {
		final IUserAccount entity = dao.getById(id);
		return entity;
	}

	@Override
	public List<IUserAccount> getAll() {
		final List<IUserAccount> all = dao.getAll();
		return all;
	}

	@Override
	public void delete(final Integer id) {
		dao.delete(id);
		LOGGER.info("User account id={} was deleted.", id);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
		LOGGER.info("All user accounts was deleted.");
	}

	@Override
	public List<IUserAccount> find(final UserAccountFilter filter) {
		return dao.find(filter);
	}

	@Override
	public long getCount() {
		return dao.getCount();
	}

	@Override
	public IUserAccount getByEmail(final String email) {
		final IUserAccount entity = dao.getByEmail(email);
		return entity;
	}

	@Override
	public boolean validateEmail(final String email) {
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
