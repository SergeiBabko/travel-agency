package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;

public class UserAccountServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final IUserAccount entity = saveNewUserAccount();
		final IUserAccount entityFromDb = userAccountService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getEmail(), entityFromDb.getEmail());
		assertEquals(entity.getPassword(), entityFromDb.getPassword());
		assertEquals(entity.getFirstName(), entityFromDb.getFirstName());
		assertEquals(entity.getLastName(), entityFromDb.getLastName());
		assertEquals(entity.getRole(), entityFromDb.getRole());
		assertNotNull(entityFromDb.getEmail());
		assertNotNull(entityFromDb.getPassword());
		assertNotNull(entityFromDb.getRole());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final IUserAccount entity = saveNewUserAccount();

		final UserRole[] allRole = UserRole.values();
		final int randomIndex = getRANDOM().nextInt(allRole.length) + 0;

		String newEmail = entity.getEmail() + "_upd";
		String newPassword = entity.getPassword() + "_upd";
		String newFirstName = entity.getFirstName() + "_upd";
		String newLastName = entity.getLastName() + "_upd";
		UserRole newRole = allRole[randomIndex];

		entity.setEmail(newEmail);
		entity.setPassword(newPassword);
		entity.setFirstName(newFirstName);
		entity.setLastName(newLastName);
		entity.setRole(newRole);

		Thread.sleep(1000);

		try {
			userAccountService.save(entity);
			getEmailSender(2).sendEmail(entity, "Email Header", "New Password");
		} catch (NotUniqueUserException e) {
			LOGGER.debug("Not unique user.");
		}

		final IUserAccount entityFromDb = userAccountService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newEmail, entityFromDb.getEmail());
		assertEquals(newPassword, entityFromDb.getPassword());
		assertEquals(newFirstName, entityFromDb.getFirstName());
		assertEquals(newLastName, entityFromDb.getLastName());
		assertEquals(newRole, entityFromDb.getRole());

		assertNotNull(entityFromDb.getEmail());
		assertNotNull(entityFromDb.getPassword());
		assertNotNull(entityFromDb.getFirstName());
		assertNotNull(entityFromDb.getLastName());
		assertNotNull(entityFromDb.getRole());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = userAccountService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewUserAccount();
		}

		final List<IUserAccount> allEntities = userAccountService.getAll();

		for (final IUserAccount entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getEmail());
			assertNotNull(entityFromDb.getPassword());
			assertNotNull(entityFromDb.getRole());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

		assertEquals(randomObjectsCount + initialCount, allEntities.size());

	}

	@Test
	public void testDelete() {

		LOGGER.debug("testDelete:");

		final IUserAccount entity = saveNewUserAccount();

		userAccountService.delete(entity.getId());
		assertNull(userAccountService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewUserAccount();
		}

		userAccountService.deleteAll();
		assertEquals(0, userAccountService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewUserAccount();
		}

		long count = userAccountService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testGetByEmail() {

		LOGGER.debug("testGetByEmail:");

		for (int i = 1; i < 16; i++) {
			IUserAccount user = saveNewUserAccount();
			user.setEmail("Email" + i);
			try {
				userAccountService.save(user);
			} catch (NotUniqueUserException e) {
				LOGGER.debug("Not unique user.");
			}
		}

		final String randomEmail = "Email" + getRandomCount();

		LOGGER.debug("Random Email={}.", randomEmail);

		try {
			final IUserAccount entity = userAccountService.getByEmail(randomEmail);
			if (entity != null) {
				LOGGER.debug("Found 1 entity: {}", entity);
				assertNotNull(entity);
				assertNotNull(entity.getEmail());
				assertNotNull(entity.getPassword());
				assertNotNull(entity.getRole());
				assertNotNull(entity.getId());
				assertNotNull(entity.getCreated());
				assertNotNull(entity.getUpdated());
				assertEquals(randomEmail, entity.getEmail());
			}
		} catch (IllegalArgumentException e) {
			LOGGER.debug("No matches found.");
		}

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final UserAccountFilter filter = new UserAccountFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = userAccountService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = userAccountService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = userAccountService.find(filter).size();
		filter.setSortColumn("role");
		final int initialCount4 = userAccountService.find(filter).size();
		filter.setSortColumn("email");
		final int initialCount5 = userAccountService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewUserAccount();
		}

		final List<IUserAccount> allEntities = userAccountService.find(filter);

		for (final IUserAccount entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getEmail());
			assertNotNull(entityFromDb.getPassword());
			assertNotNull(entityFromDb.getRole());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

		assertEquals(randomObjectsCount + initialCount1, allEntities.size());
		assertEquals(randomObjectsCount + initialCount2, allEntities.size());
		assertEquals(randomObjectsCount + initialCount3, allEntities.size());
		assertEquals(randomObjectsCount + initialCount4, allEntities.size());
		assertEquals(randomObjectsCount + initialCount5, allEntities.size());

	}

	@Test
	public void testEmailValidation() {

		LOGGER.debug("testEmailValidation:");

		final IUserAccount entity = saveNewUserAccount();

		if (verify() == 1) {
			entity.setEmail("someuseremail@gmail.com");
			try {
				userAccountService.save(entity);
			} catch (NotUniqueUserException e) {
				LOGGER.debug("Not unique user.");
			}
		} else {
			entity.setEmail("someuseremail@gmail");
			try {
				userAccountService.save(entity);
			} catch (NotUniqueUserException e) {
				LOGGER.debug("Not unique user.");
			}
		}

		final IUserAccount entityFromDb = userAccountService.getById(entity.getId());

		boolean valid = userAccountService.validateEmail(entityFromDb.getEmail());
		LOGGER.debug("Email: {}, is valid: {}", entityFromDb.getEmail(), valid);

		assertNotNull(entityFromDb);
		assertEquals(entity.getEmail(), entityFromDb.getEmail());

		if (!valid) {
			assertFalse(valid);
		} else {
			assertTrue(valid);
		}

	}

}