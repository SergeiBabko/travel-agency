package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;

public class TaDescriptionServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaDescriptionServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ITaDescription entity = saveNewTaDescription();
		final ITaDescription entityFromDb = taDescriptionService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getDescription(), entityFromDb.getDescription());
		assertEquals(entity.getContacts(), entityFromDb.getContacts());
		assertEquals(entity.getAddress(), entityFromDb.getAddress());
		assertEquals(entity.getImage1(), entityFromDb.getImage1());
		assertEquals(entity.getImage2(), entityFromDb.getImage2());
		assertEquals(entity.getImage3(), entityFromDb.getImage3());
		assertNotNull(entityFromDb.getDescription());
		assertNotNull(entityFromDb.getContacts());
		assertNotNull(entityFromDb.getAddress());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ITaDescription entity = saveNewTaDescription();

		String newDescription = entity.getDescription() + "_updated";
		String newContacts = entity.getContacts() + "_updated";
		String newAddress = entity.getAddress() + "_updated";
		String newImage1 = entity.getImage1() + "_updated";
		String newImage2 = entity.getImage2() + "_updated";
		String newImage3 = entity.getImage3() + "_updated";

		entity.setDescription(newDescription);
		entity.setContacts(newContacts);
		entity.setAddress(newAddress);
		entity.setImage1(newImage1);
		entity.setImage2(newImage2);
		entity.setImage3(newImage3);

		Thread.sleep(1000);
		taDescriptionService.save(entity);

		final ITaDescription entityFromDb = taDescriptionService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getDescription(), entityFromDb.getDescription());
		assertEquals(entity.getContacts(), entityFromDb.getContacts());
		assertEquals(entity.getAddress(), entityFromDb.getAddress());
		assertEquals(entity.getImage1(), entityFromDb.getImage1());
		assertEquals(entity.getImage2(), entityFromDb.getImage2());
		assertEquals(entity.getImage3(), entityFromDb.getImage3());

		assertNotNull(entityFromDb.getDescription());
		assertNotNull(entityFromDb.getContacts());
		assertNotNull(entityFromDb.getAddress());
		assertNotNull(entityFromDb.getImage1());
		assertNotNull(entityFromDb.getImage2());
		assertNotNull(entityFromDb.getImage3());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = taDescriptionService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTaDescription();
		}

		final List<ITaDescription> allEntities = taDescriptionService.getAll();

		for (final ITaDescription entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getDescription());
			assertNotNull(entityFromDb.getContacts());
			assertNotNull(entityFromDb.getAddress());
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

		final ITaDescription entity = saveNewTaDescription();

		taDescriptionService.delete(entity.getId());
		assertNull(taDescriptionService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTaDescription();
		}

		taDescriptionService.deleteAll();
		assertEquals(0, taDescriptionService.getAll().size());

	}

}