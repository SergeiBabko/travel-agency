package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;

public class TourTypeServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourTypeServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ITourType entity = saveNewTourType();
		final ITourType entityFromDb = tourTypeService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getType(), entityFromDb.getType());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getType());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ITourType entity = saveNewTourType();

		String newType = entity.getType() + "_updated";

		entity.setType(newType);

		Thread.sleep(1000);
		tourTypeService.save(entity);

		final ITourType entityFromDb = tourTypeService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newType, entityFromDb.getType());

		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getType());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = tourTypeService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTourType();
		}

		final List<ITourType> allEntities = tourTypeService.getAll();

		for (final ITourType entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getType());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

		assertEquals(randomObjectsCount + initialCount, allEntities.size());

	}

	@Test
	public void testDelete() {

		LOGGER.debug("testDelete:");

		final ITourType entity = saveNewTourType();

		tourTypeService.delete(entity.getId());
		assertNull(tourTypeService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTourType();
		}

		tourTypeService.deleteAll();
		assertEquals(0, tourTypeService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTourType();
		}

		long count = tourTypeService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

}