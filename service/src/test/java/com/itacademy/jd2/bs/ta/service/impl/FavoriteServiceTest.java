package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;

public class FavoriteServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final IFavorite entity = saveNewFavorite();
		final IFavorite entityFromDb = favoriteService.getById(entity.getId());

		assertNotNull(entityFromDb);
//		assertEquals(entity.getCustomer().getId(), entityFromDb.getCustomer().getId());
//		assertEquals(entity.getTour().getId(), entityFromDb.getTour().getId());
//		assertNotNull(entityFromDb.getCustomer().getId());
//		assertNotNull(entityFromDb.getTour().getId());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final IFavorite entity = saveNewFavorite();
		final ICustomer customer = saveNewCustomer();
		final ITour tour = saveNewTour();

		entity.setCustomer(customer);
		entity.setTour(tour);

		Thread.sleep(1000);
		favoriteService.save(entity);

		final IFavorite entityFromDb = favoriteService.getById(entity.getId());

		assertNotNull(entityFromDb);
//		assertEquals(customer.getId(), entityFromDb.getCustomer().getId());
//		assertEquals(tour.getId(), entityFromDb.getTour().getId());

//		assertNotNull(entityFromDb.getCustomer().getId());
//		assertNotNull(entityFromDb.getTour().getId());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = favoriteService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewFavorite();
		}

		final List<IFavorite> allEntities = favoriteService.getAll();

		for (final IFavorite entityFromDb : allEntities) {
//			assertNotNull(entityFromDb.getCustomer().getId());
//			assertNotNull(entityFromDb.getTour().getId());
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

		final IFavorite entity = saveNewFavorite();

		favoriteService.delete(entity.getId());
		assertNull(favoriteService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewFavorite();
		}

		favoriteService.deleteAll();
		assertEquals(0, favoriteService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewFavorite();
		}

		long count = favoriteService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testGetCountByTourId() {

		LOGGER.debug("testGetCountByTourId:");

		final int randomObjectsCount = getRandomCount();
		Integer tourId = null;

		for (int i = 0; i < randomObjectsCount; i++) {
			IFavorite entity = saveNewFavorite();
			tourId = entity.getTour().getId();
		}

		long count = favoriteService.getCountByTourId(tourId);

		LOGGER.debug("Count= " + count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final FavoriteFilter filter = new FavoriteFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = favoriteService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = favoriteService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = favoriteService.find(filter).size();
		filter.setSortColumn("tourName");
		final int initialCount4 = favoriteService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewFavorite();
		}

		final List<IFavorite> allEntities = favoriteService.find(filter);

		for (final IFavorite entityFromDb : allEntities) {
//			assertNotNull(entityFromDb.getCustomer().getId());
//			assertNotNull(entityFromDb.getTour().getId());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

		assertEquals(randomObjectsCount + initialCount1, allEntities.size());
		assertEquals(randomObjectsCount + initialCount2, allEntities.size());
		assertEquals(randomObjectsCount + initialCount3, allEntities.size());
		assertEquals(randomObjectsCount + initialCount4, allEntities.size());

	}

	@Test
	public void testGetByCustomerId() {

		LOGGER.debug("testGetByCustomerId:");

		final int randomObjectsCount = getRandomCount();

		Integer id = 0;

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewFavorite();
		}
		final List<IFavorite> allEntitiesFromDb = favoriteService.getAll();

		for (final IFavorite entityFromDb : allEntitiesFromDb) {
			if (entityFromDb.getId() > randomObjectsCount) {
				id = entityFromDb.getId();
				break;
			} else {
				id = entityFromDb.getId();
			}
		}

		final FavoriteFilter filter = new FavoriteFilter();

		final List<IFavorite> entitiesFromDb = favoriteService.getByCustomerId(id, filter);

		for (final IFavorite entityFromDb : entitiesFromDb) {
			assertNotNull(entityFromDb.getCustomer().getId());
			assertNotNull(entityFromDb.getTour().getId());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
			assertTrue(id.equals((entityFromDb.getId())));
		}

	}

}