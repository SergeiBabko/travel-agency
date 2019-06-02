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
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;

public class ReviewServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final IReview entity = saveNewReview();
		final IReview entityFromDb = reviewService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getReview(), entityFromDb.getReview());
		assertEquals(entity.getRating(), entityFromDb.getRating());
//		assertEquals(entity.getCustomer().getId(), entityFromDb.getCustomer().getId());
//		assertEquals(entity.getTourDate().getId(), entityFromDb.getTourDate().getId());
		assertNotNull(entityFromDb.getReview());
		assertNotNull(entityFromDb.getRating());
//		assertNotNull(entityFromDb.getCustomer().getId());
//		assertNotNull(entityFromDb.getTourDate().getId());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final IReview entity = saveNewReview();
		final ICustomer customer = saveNewCustomer();
		final ITourDate tourDate = saveNewTourDate();

		String newReview = entity.getReview() + "_upd";
		Integer newRating = entity.getRating() + getRANDOM().nextInt(5 - 3 + 1) + 3;

		entity.setReview(newReview);
		entity.setRating(newRating);
		entity.setCustomer(customer);
		entity.setTourDate(tourDate);

		Thread.sleep(1000);
		reviewService.save(entity);

		final IReview entityFromDb = reviewService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newReview, entityFromDb.getReview());
		assertEquals(newRating, entityFromDb.getRating());
//		assertEquals(customer.getId(), entityFromDb.getCustomer().getId());
//		assertEquals(tourDate.getId(), entityFromDb.getTourDate().getId());

		assertNotNull(entityFromDb.getReview());
		assertNotNull(entityFromDb.getRating());
//		assertNotNull(entityFromDb.getCustomer().getId());
//		assertNotNull(entityFromDb.getTourDate().getId());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = reviewService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewReview();
		}

		final List<IReview> allEntities = reviewService.getAll();

		for (final IReview entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getReview());
			assertNotNull(entityFromDb.getRating());
//			assertNotNull(entityFromDb.getCustomer().getId());
//			assertNotNull(entityFromDb.getTourDate().getId());
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

		final IReview entity = saveNewReview();

		reviewService.delete(entity.getId());
		assertNull(reviewService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewReview();
		}

		reviewService.deleteAll();
		assertEquals(0, reviewService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewReview();
		}

		long count = reviewService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final ReviewFilter filter = new ReviewFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = reviewService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = reviewService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = reviewService.find(filter).size();
		filter.setSortColumn("rating");
		final int initialCount4 = reviewService.find(filter).size();
		filter.setSortColumn("tourDate");
		final int initialCount5 = reviewService.find(filter).size();
		filter.setSortColumn("tourName");
		final int initialCount6 = reviewService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewReview();
		}

		final List<IReview> allEntities = reviewService.find(filter);

		for (final IReview entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getReview());
			assertNotNull(entityFromDb.getRating());
//			assertNotNull(entityFromDb.getCustomer().getId());
//			assertNotNull(entityFromDb.getTourDate().getId());
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
		assertEquals(randomObjectsCount + initialCount6, allEntities.size());

	}

	@Test
	public void testGetByCustomerId() {

		LOGGER.debug("testGetByCustomerId:");

		final int randomObjectsCount = getRandomCount();

		Integer id = 0;

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewReview();
		}

		final ReviewFilter filter = new ReviewFilter();
		final List<IReview> allEntitiesFromDb = reviewService.find(filter);

		for (final IReview entityFromDb : allEntitiesFromDb) {
			if (entityFromDb.getId() > randomObjectsCount) {
				id = entityFromDb.getCustomer().getId();
				break;
			} else {
				id = entityFromDb.getCustomer().getId();
			}
		}

		final List<IReview> entitiesFromDb = reviewService.getByCustomerId(filter, id);

		for (final IReview entityFromDb : entitiesFromDb) {
			assertNotNull(entityFromDb.getCustomer().getId());
			assertNotNull(entityFromDb.getTourDate().getId());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
			assertTrue(id.equals((entityFromDb.getCustomer().getId())));
		}

	}

	@Test
	public void testGetByTourId() {

		LOGGER.debug("testGetByTourId:");

		final int randomObjectsCount = getRandomCount();

		Integer id = 0;

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewReview();
		}

		final ReviewFilter filter = new ReviewFilter();
		final List<IReview> allEntitiesFromDb = reviewService.find(filter);

		for (final IReview entityFromDb : allEntitiesFromDb) {
			if (entityFromDb.getId() > randomObjectsCount) {
				id = entityFromDb.getTourDate().getTour().getId();
				break;
			} else {
				id = entityFromDb.getTourDate().getTour().getId();
			}
		}

		final List<IReview> entitiesFromDb = reviewService.getByTourId(filter, id);

		for (final IReview entityFromDb : entitiesFromDb) {
			assertNotNull(entityFromDb.getCustomer().getId());
			assertNotNull(entityFromDb.getTourDate().getId());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
			assertTrue(id.equals((entityFromDb.getTourDate().getTour().getId())));
		}

	}

}