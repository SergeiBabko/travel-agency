package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;

public class BookedServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookedServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final IBooked entity = saveNewBooked();
		final IBooked entityFromDb = bookedService.getById(entity.getId());

		assertNotNull(entityFromDb);
//		assertEquals(entity.getCustomer().getId(), entityFromDb.getCustomer().getId());
//		assertEquals(entity.getTourDate().getId(), entityFromDb.getTourDate().getId());
		assertEquals(entity.getNumPerson(), entityFromDb.getNumPerson());
		assertEquals(entity.getPrice(), entityFromDb.getPrice());
		assertEquals(entity.getMessage(), entityFromDb.getMessage());
		assertEquals(entity.getProcessed(), entityFromDb.getProcessed());
//		assertNotNull(entityFromDb.getCustomer().getId());
//		assertNotNull(entityFromDb.getTourDate().getId());
		assertNotNull(entityFromDb.getPrice());
		assertNotNull(entityFromDb.getProcessed());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final IBooked entity = saveNewBooked();
		final ICustomer customer = saveNewCustomer();
		final ITourDate tourDate = saveNewTourDate();

		if (entity.getNumPerson() == null) {
			entity.setNumPerson(0);
		}
		Integer newNumPerson = entity.getNumPerson() + getRANDOM().nextInt(10 - 3 + 1) + 3;
		Integer newPrice = entity.getPrice() + getRANDOM().nextInt(50 - 10 + 1) + 10;
		String newMessage = entity.getMessage() + "_upd";
		Boolean newProcessed = getRANDOM().nextBoolean();

		entity.setCustomer(customer);
		entity.setTourDate(tourDate);
		entity.setNumPerson(newNumPerson);
		entity.setPrice(newPrice);
		entity.setMessage(newMessage);
		entity.setProcessed(newProcessed);

		Thread.sleep(1000);
		bookedService.save(entity);

		final IBooked entityFromDb = bookedService.getById(entity.getId());

		assertNotNull(entityFromDb);
//		assertEquals(customer.getId(), entityFromDb.getCustomer().getId());
//		assertEquals(tourDate.getId(), entityFromDb.getTourDate().getId());
		assertEquals(newNumPerson, entityFromDb.getNumPerson());
		assertEquals(newPrice, entityFromDb.getPrice());
		assertEquals(newMessage, entityFromDb.getMessage());
		assertEquals(newProcessed, entityFromDb.getProcessed());

//		assertNotNull(entityFromDb.getCustomer().getId());
//		assertNotNull(entityFromDb.getTourDate().getId());
		assertNotNull(entityFromDb.getNumPerson());
		assertNotNull(entityFromDb.getPrice());
		assertNotNull(entityFromDb.getMessage());
		assertNotNull(entityFromDb.getProcessed());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = bookedService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewBooked();
		}

		final List<IBooked> allEntities = bookedService.getAll();

		for (final IBooked entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
//			assertNotNull(entityFromDb.getCustomer().getId());
//			assertNotNull(entityFromDb.getTourDate().getId());
			assertNotNull(entityFromDb.getPrice());
			assertNotNull(entityFromDb.getProcessed());
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

		final IBooked entity = saveNewBooked();

		boolean exist = true;

		bookedService.delete(entity.getId());

		try {
			bookedService.getById(entity.getId());
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
			saveNewBooked();
		}

		bookedService.deleteAll();
		assertEquals(0, bookedService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewBooked();
		}

		long count = bookedService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testGetCountByTourId() {

		LOGGER.debug("testGetCountByTourId:");

		final int randomObjectsCount = getRandomCount();

		Integer tourId = null;

		for (int i = 0; i < randomObjectsCount; i++) {
			IBooked entity = saveNewBooked();
			tourId = entity.getTourDate().getTour().getId();
		}

		long count = bookedService.getCountByTourId(tourId);

		LOGGER.debug("Count= " + count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final BookedFilter filter = new BookedFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = bookedService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = bookedService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = bookedService.find(filter).size();
		filter.setSortColumn("processed");
		final int initialCount4 = bookedService.find(filter).size();
		filter.setSortColumn("price");
		final int initialCount5 = bookedService.find(filter).size();
		filter.setSortColumn("tourDate");
		final int initialCount6 = bookedService.find(filter).size();
		filter.setSortColumn("tourName");
		final int initialCount7 = bookedService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewBooked();
		}

		final List<IBooked> allEntities = bookedService.find(filter);

		for (final IBooked entityFromDb : allEntities) {
			assertNotNull(entityFromDb.getPrice());
			assertNotNull(entityFromDb.getProcessed());
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
		assertEquals(randomObjectsCount + initialCount7, allEntities.size());

	}

	@Test
	public void testGetByCustomerId() {

		LOGGER.debug("testGetByCustomerId:");

		final int randomObjectsCount = getRandomCount();

		Integer id = 0;

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewBooked();
		}
		final BookedFilter filter = new BookedFilter();
		final List<IBooked> allEntitiesFromDb = bookedService.find(filter);

		for (final IBooked entityFromDb : allEntitiesFromDb) {
			if (entityFromDb.getId() > randomObjectsCount) {
				id = entityFromDb.getCustomer().getId();
				break;
			} else {
				id = entityFromDb.getCustomer().getId();
			}
		}

		final List<IBooked> entitiesFromDb = bookedService.getByCustomerId(filter, id);

		for (final IBooked entityFromDb : entitiesFromDb) {
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
	public void getBookedByMonth() {

		LOGGER.debug("getBookedByMonth:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewBooked();
		}
		final Map<Integer, Long> map = bookedService.getBookedByMonth();

		for (int i = 1; i <= map.size(); i++) {
			LOGGER.debug("Month: {}, Count: {}", i, map.get(i));
		}

	}

}