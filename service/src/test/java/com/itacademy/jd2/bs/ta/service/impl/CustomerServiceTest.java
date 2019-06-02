package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;

public class CustomerServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ICustomer entity = saveNewCustomer();
		final ICustomer entityFromDb = customerService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getStatus(), entityFromDb.getStatus());
		assertEquals(entity.getMiddleName(), entityFromDb.getMiddleName());
		assertEquals(entity.getBirthday(), entityFromDb.getBirthday());
		assertEquals(entity.getPassportStart(), entityFromDb.getPassportStart());
		assertEquals(entity.getPassportEnd(), entityFromDb.getPassportEnd());
		assertEquals(entity.getPassportNumber(), entityFromDb.getPassportNumber());
		assertEquals(entity.getPhoneNumber(), entityFromDb.getPhoneNumber());
		assertEquals(entity.getCountry(), entityFromDb.getCountry());
		assertEquals(entity.getCity(), entityFromDb.getCity());
		assertEquals(entity.getStreet(), entityFromDb.getStreet());
		assertEquals(entity.getBonus(), entityFromDb.getBonus());
		assertNotNull(entityFromDb.getStatus());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ICustomer entity = saveNewCustomer();

		final CustomerStatus[] allStatuses = CustomerStatus.values();
		final int randomIndex = getRANDOM().nextInt(allStatuses.length) + 0;

		CustomerStatus newStatus = allStatuses[randomIndex];
		String newMiddleName = entity.getMiddleName() + "_upd";
		String newBirthday = entity.getBirthday() + "_upd";
		String newPStart = entity.getPassportStart() + "_upd";
		String newPEnd = entity.getPassportEnd() + "_upd";
		String newPNumber = entity.getPassportNumber() + "_upd";
		if (entity.getPhoneNumber() == null) {
			entity.setPhoneNumber("");
		}
		String newPhoneNumber = entity.getPhoneNumber() + getRandomPrefix();
		String newCountry = entity.getCountry() + "_upd";
		String newCity = entity.getCity() + "_upd";
		String newStreet = entity.getStreet() + "_upd";
		Integer newBonus = getRandomCount();

		entity.setStatus(newStatus);
		entity.setMiddleName(newMiddleName);
		entity.setBirthday(newBirthday);
		entity.setPassportStart(newPStart);
		entity.setPassportEnd(newPEnd);
		entity.setPassportNumber(newPNumber);
		entity.setPhoneNumber(newPhoneNumber);
		entity.setCountry(newCountry);
		entity.setCity(newCity);
		entity.setStreet(newStreet);

		if (entity.getBonus() == null) {

			entity.setBonus(newBonus);
		} else {
			newBonus = entity.getBonus() + getRandomCount();
			entity.setBonus(newBonus);
		}

		Thread.sleep(1000);
		customerService.update(entity);

		final ICustomer entityFromDb = customerService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newStatus, entityFromDb.getStatus());
		assertEquals(newMiddleName, entityFromDb.getMiddleName());
		assertEquals(newBirthday, entityFromDb.getBirthday());
		assertEquals(newPStart, entityFromDb.getPassportStart());
		assertEquals(newPEnd, entityFromDb.getPassportEnd());
		assertEquals(newPNumber, entityFromDb.getPassportNumber());
		assertEquals(newPhoneNumber, entityFromDb.getPhoneNumber());
		assertEquals(newCountry, entityFromDb.getCountry());
		assertEquals(newCity, entityFromDb.getCity());
		assertEquals(newStreet, entityFromDb.getStreet());
		assertEquals(newBonus, entityFromDb.getBonus());

		assertNotNull(entityFromDb.getMiddleName());
		assertNotNull(entityFromDb.getBirthday());
		assertNotNull(entityFromDb.getBirthday());
		assertNotNull(entityFromDb.getPassportEnd());
		assertNotNull(entityFromDb.getPassportNumber());
		assertNotNull(entityFromDb.getPhoneNumber());
		assertNotNull(entityFromDb.getCountry());
		assertNotNull(entityFromDb.getCity());
		assertNotNull(entityFromDb.getStreet());
		assertNotNull(entityFromDb.getStatus());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getBonus());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = customerService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCustomer();
		}

		final List<ICustomer> allEntities = customerService.getAll();

		for (final ICustomer entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getStatus());
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

		final ICustomer entity = saveNewCustomer();

		customerService.delete(entity.getId());

		final ICustomer entityFromDb = customerService.getById(entity.getId());

		LOGGER.info(entityFromDb.toString());

		assertTrue(entityFromDb.getStatus().equals(CustomerStatus.deleted));

	}

	@Test
	public void activateOrDeactivate() {

		LOGGER.debug("activateOrDeactivate:");

		final ICustomer entity = saveNewCustomer();

		customerService.activateOrDeactivate(entity.getId());

		final ICustomer entityFromDb = customerService.getById(entity.getId());

		LOGGER.info(entityFromDb.toString());

		assertTrue(entityFromDb.getStatus().equals(CustomerStatus.blocked));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCustomer();
		}

		customerService.deleteAll();
		assertEquals(0, customerService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCustomer();
		}

		long count = customerService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final CustomerFilter filter = new CustomerFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = customerService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = customerService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = customerService.find(filter).size();
		filter.setSortColumn("status");
		final int initialCount4 = customerService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCustomer();
		}

		final List<ICustomer> allEntities = customerService.find(filter);

		for (final ICustomer entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getStatus());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertNotNull(entityFromDb.getUserAccount());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

		assertEquals(randomObjectsCount + initialCount1, allEntities.size());
		assertEquals(randomObjectsCount + initialCount2, allEntities.size());
		assertEquals(randomObjectsCount + initialCount3, allEntities.size());
		assertEquals(randomObjectsCount + initialCount4, allEntities.size());

	}

}