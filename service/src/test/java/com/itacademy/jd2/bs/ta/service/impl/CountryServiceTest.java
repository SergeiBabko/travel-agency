package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.filter.CountryFilter;

public class CountryServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ICountry entity = saveNewCountry();
		final ICountry entityFromDb = countryService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getName(), entityFromDb.getName());
		assertNotNull(entityFromDb.getName());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ICountry entity = saveNewCountry();

		String newName = entity.getName() + "_updated";

		entity.setName(newName);

		Thread.sleep(1000);
		countryService.save(entity);

		final ICountry entityFromDb = countryService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newName, entityFromDb.getName());

		assertNotNull(entityFromDb.getName());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = countryService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCountry();
		}

		final List<ICountry> allEntities = countryService.getAll();

		for (final ICountry entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getName());
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

		final ICountry entity = saveNewCountry();

		countryService.delete(entity.getId());
		assertNull(countryService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCountry();
		}

		countryService.deleteAll();
		assertEquals(0, countryService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCountry();
		}

		long count = countryService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final CountryFilter filter = new CountryFilter();
		filter.setSortColumn("name");
		filter.setSortOrder(true);

		final int initialCount = countryService.find(filter).size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCountry();
		}

		final List<ICountry> allEntities = countryService.find(filter);

		for (final ICountry entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getName());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

		assertEquals(randomObjectsCount + initialCount, allEntities.size());

	}

}