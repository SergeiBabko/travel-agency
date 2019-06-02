package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;

public class CityServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ICity entity = saveNewCity();
		final ICity entityFromDb = cityService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getName(), entityFromDb.getName());
//		assertEquals(entity.getCountry().getId(), entityFromDb.getCountry().getId());
		assertNotNull(entityFromDb.getName());
//		assertNotNull(entityFromDb.getCountry().getId());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ICity entity = saveNewCity();
		final ICountry country = saveNewCountry();

		String newName = entity.getName() + "_updated";

		entity.setName(newName);
		entity.setCountry(country);

		Thread.sleep(1000);
		cityService.save(entity);

		final ICity entityFromDb = cityService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newName, entityFromDb.getName());
//		assertEquals(country.getId(), entityFromDb.getCountry().getId());

		assertNotNull(entityFromDb.getName());
//		assertNotNull(entityFromDb.getCountry().getId());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = cityService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCity();
		}

		final List<ICity> allEntities = cityService.getAll();

		for (final ICity entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getName());
//			assertNotNull(entityFromDb.getCountry().getId());
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

		final ICity entity = saveNewCity();

		boolean exist = true;

		cityService.delete(entity.getId());

		try {
			cityService.getById(entity.getId());
		} catch (NoResultException e) {
			exist = false;
		}

		assertFalse(exist);

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCity();
		}

		cityService.deleteAll();
		assertEquals(0, cityService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewCity();
		}

		long count = cityService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		for (int i = 0; i < 3; i++) {
			final CityFilter filter = new CityFilter();
			if (i == 0) {
				filter.setSortColumn("name");
				filter.setSortOrder(true);
			}
			if (i == 1) {
				filter.setSortColumn("country");
				filter.setSortOrder(true);
			}
			if (i == 2) {
				filter.setSortColumn("name");
			}
			final int initialCount = cityService.find(filter).size();
			final int randomObjectsCount = getRandomCount();

			for (int j = 0; j < randomObjectsCount; j++) {
				saveNewCity();
			}

			final List<ICity> allEntities = cityService.find(filter);

			for (final ICity entityFromDb : allEntities) {
				assertNotNull(entityFromDb);
				assertNotNull(entityFromDb.getName());
//				assertNotNull(entityFromDb.getCountry().getId());
				assertNotNull(entityFromDb.getId());
				assertNotNull(entityFromDb.getCreated());
				assertNotNull(entityFromDb.getUpdated());
				assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
			}

			assertEquals(randomObjectsCount + initialCount, allEntities.size());
		}

	}

}