package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;

public class TourDateServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourDateServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ITourDate entity = saveNewTourDate();
		final ITourDate entityFromDb = tourDateService.getById(entity.getId());

		assertNotNull(entityFromDb);
//		assertEquals(entity.getTour().getId(), entityFromDb.getTour().getId());
		assertEquals(entity.getNumPerson(), entityFromDb.getNumPerson());
		assertEquals(entity.getDateStart(), entityFromDb.getDateStart());
		assertEquals(entity.getDateEnd(), entityFromDb.getDateEnd());
//		assertNotNull(entityFromDb.getTour().getId());
		assertNotNull(entityFromDb.getNumPerson());
		assertNotNull(entityFromDb.getDateStart());
		assertNotNull(entityFromDb.getDateEnd());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ITourDate entity = saveNewTourDate();
		final ITour tour = saveNewTour();
		LocalDate date = LocalDate.now().plusDays(10);

		Integer newNumPerson = entity.getNumPerson() + getRANDOM().nextInt(10 - 3 + 1) + 3;
		Date newDateStart = getDateWithowtTime(date);
		date = date.plusDays(getRANDOM().nextInt(10 - 5 + 1) + 5);
		Date newDateEnd = getDateWithowtTime(date);

		entity.setTour(tour);
		entity.setNumPerson(newNumPerson);
		entity.setDateStart(newDateStart);
		entity.setDateEnd(newDateEnd);

		Thread.sleep(1000);
		tourDateService.save(entity);

		final ITourDate entityFromDb = tourDateService.getById(entity.getId());

		assertNotNull(entityFromDb);
//		assertEquals(tour.getId(), entityFromDb.getTour().getId());
		assertEquals(newNumPerson, entityFromDb.getNumPerson());
		assertEquals(newDateStart, entityFromDb.getDateStart());
		assertEquals(newDateEnd, entityFromDb.getDateEnd());

//		assertNotNull(entityFromDb.getTour().getId());
		assertNotNull(entityFromDb.getNumPerson());
		assertNotNull(entityFromDb.getDateStart());
		assertNotNull(entityFromDb.getDateEnd());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = tourDateService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTourDate();
		}

		final List<ITourDate> allEntities = tourDateService.getAll();

		for (final ITourDate entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
//			assertNotNull(entityFromDb.getTour().getId());
			assertNotNull(entityFromDb.getNumPerson());
			assertNotNull(entityFromDb.getDateStart());
			assertNotNull(entityFromDb.getDateEnd());
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

		final ITourDate entity = saveNewTourDate();

		tourDateService.delete(entity.getId());
		assertNull(tourDateService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTourDate();
		}

		tourDateService.deleteAll();
		assertEquals(0, tourDateService.getAll().size());

	}

	@Test
	public void testGetByTourId() {

		LOGGER.debug("testGetByTourId:");

		final TourDateFilter filter = new TourDateFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("dateStart");

		final int randomObjectsCount = getRandomCount();

		int tourId = 0;

		for (int i = 0; i < randomObjectsCount; i++) {
			final ITour tour = saveNewTour();
			tourId = tour.getId();
		}

		final List<ITourDate> allEntities = tourDateService.getByTourId(filter, tourId);

		for (final ITourDate entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getDateStart());
			assertNotNull(entityFromDb.getDateEnd());
			assertNotNull(entityFromDb.getNumPerson());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

	}

}