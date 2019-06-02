package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;

public class TourServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final ITour entity = saveNewTour();
		final ITour entityFromDb = tourService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getTourStatus(), entityFromDb.getTourStatus());
		assertEquals(entity.getName(), entityFromDb.getName());
		assertEquals(entity.getPrice(), entityFromDb.getPrice());
		assertEquals(entity.getTourType().getId(), entityFromDb.getTourType().getId());
		assertEquals(entity.getNights(), entityFromDb.getNights());
		assertEquals(entity.getImage(), entityFromDb.getImage());
		assertEquals(entity.getDescription(), entityFromDb.getDescription());
		assertEquals(entity.getCity().getId(), entityFromDb.getCity().getId());
		assertEquals(entity.getAddress(), entityFromDb.getAddress());
		assertNotNull(entityFromDb.getTourStatus());
		assertNotNull(entityFromDb.getName());
		assertNotNull(entityFromDb.getPrice());
		assertNotNull(entityFromDb.getTourType().getId());
		assertNotNull(entityFromDb.getNights());
		assertNotNull(entityFromDb.getDescription());
		assertNotNull(entityFromDb.getCity().getId());
		assertNotNull(entityFromDb.getAddress());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final ITour entity = saveNewTour();
		final ICity city = saveNewCity();
		final ITourType tourType = saveNewTourType();

		final TourStatus[] allStatuses = TourStatus.values();
		final int randomIndex = getRANDOM().nextInt(allStatuses.length) + 0;

		TourStatus newTourStatus = allStatuses[randomIndex];
		String newName = entity.getName() + "_upd";
		Integer newPrice = entity.getPrice() + getRANDOM().nextInt(100 + 100 + 1) - 100;
		Integer newNights = entity.getNights() + getRANDOM().nextInt(5 + 5 + 1) - 5;
		String newImage = entity.getImage() + "_upd";
		String newDescription = entity.getDescription() + "_upd";
		String newAddress = entity.getAddress() + "_upd";

		entity.setTourStatus(newTourStatus);
		entity.setName(newName);
		entity.setPrice(newPrice);
		entity.setTourType(tourType);
		entity.setNights(newNights);
		entity.setImage(newImage);
		entity.setDescription(newDescription);
		entity.setCity(city);
		entity.setAddress(newAddress);

		Thread.sleep(1000);
		tourService.save(entity);

		final ITour entityFromDb = tourService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newTourStatus, entityFromDb.getTourStatus());
		assertEquals(newName, entityFromDb.getName());
		assertEquals(newPrice, entityFromDb.getPrice());
		assertEquals(tourType.getId(), entityFromDb.getTourType().getId());
		assertEquals(newNights, entityFromDb.getNights());
		assertEquals(newImage, entityFromDb.getImage());
		assertEquals(newDescription, entityFromDb.getDescription());
		assertEquals(city.getId(), entityFromDb.getCity().getId());
		assertEquals(newAddress, entityFromDb.getAddress());

		assertNotNull(entityFromDb.getTourStatus());
		assertNotNull(entityFromDb.getName());
		assertNotNull(entityFromDb.getPrice());
		assertNotNull(entityFromDb.getTourType().getId());
		assertNotNull(entityFromDb.getNights());
		assertNotNull(entityFromDb.getImage());
		assertNotNull(entityFromDb.getDescription());
		assertNotNull(entityFromDb.getCity().getId());
		assertNotNull(entityFromDb.getAddress());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = tourService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTour();
		}

		final List<ITour> allEntities = tourService.getAll();

		for (final ITour entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getTourStatus());
			assertNotNull(entityFromDb.getName());
			assertNotNull(entityFromDb.getPrice());
			assertNotNull(entityFromDb.getNights());
			assertNotNull(entityFromDb.getDescription());
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

		final ITour entity = saveNewTour();

		tourService.delete(entity.getId());

		final ITour entityFromDb = tourService.getById(entity.getId());

		LOGGER.info(entityFromDb.toString());

		assertTrue(entityFromDb.getTourStatus().equals(TourStatus.inactive));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTour();
		}

		tourService.deleteAll();
		assertEquals(0, tourService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTour();
		}

		long count = tourService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final TourFilter filter = new TourFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = tourService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = tourService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = tourService.find(filter).size();
		filter.setSortColumn("name");
		final int initialCount4 = tourService.find(filter).size();
		filter.setSortColumn("city");
		final int initialCount5 = tourService.find(filter).size();
		filter.setSortColumn("country");
		final int initialCount6 = tourService.find(filter).size();
		filter.setSortColumn("nights");
		final int initialCount7 = tourService.find(filter).size();
		filter.setSortColumn("price");
		final int initialCount8 = tourService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTour();
		}

		final List<ITour> allEntities = tourService.find(filter);

		for (final ITour entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getTourStatus());
			assertNotNull(entityFromDb.getName());
			assertNotNull(entityFromDb.getPrice());
			assertNotNull(entityFromDb.getTourType().getId());
			assertNotNull(entityFromDb.getNights());
			assertNotNull(entityFromDb.getDescription());
			assertNotNull(entityFromDb.getCity().getId());
			assertNotNull(entityFromDb.getAddress());
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
		assertEquals(randomObjectsCount + initialCount8, allEntities.size());

		ITour entity = saveNewTour();
		filter.setCityId(entity.getCity().getId());
		filter.setCountryId(entity.getCity().getCountry().getId());
		filter.setNightsMin(0);
		filter.setNightsMax(entity.getNights());
		filter.setPriceMin(0);
		filter.setPriceMax(entity.getPrice());
		filter.setTourTypeId(entity.getTourType().getId());

		final List<ITour> allEntities2 = tourService.find(filter);
		for (final ITour entityFromDb : allEntities2) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getTourStatus());
			assertNotNull(entityFromDb.getName());
			assertNotNull(entityFromDb.getPrice());
			assertNotNull(entityFromDb.getTourType().getId());
			assertNotNull(entityFromDb.getNights());
			assertNotNull(entityFromDb.getDescription());
			assertNotNull(entityFromDb.getCity().getId());
			assertNotNull(entityFromDb.getAddress());
			assertNotNull(entityFromDb.getId());
			assertNotNull(entityFromDb.getCreated());
			assertNotNull(entityFromDb.getUpdated());
			assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
		}

	}

	@Test
	public void testGetMaxPrice() {

		LOGGER.debug("testGetMaxPrice:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTour();
		}

		final List<ITour> allEntities = tourService.getAll();

		for (final ITour entityFromDb : allEntities) {
			LOGGER.debug("Tour: {}, price: {}.", entityFromDb.getId(), entityFromDb.getPrice().toString());
		}

		final Integer maxPrice = tourService.getMaxPrice();
		LOGGER.debug("Max price: {}.", maxPrice.toString());

		assertEquals(maxPrice, tourService.getMaxPrice());

	}

	@Test
	public void testGetMaxNights() {

		LOGGER.debug("testGetMaxNights:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewTour();
		}

		final List<ITour> allEntities = tourService.getAll();

		for (final ITour entityFromDb : allEntities) {
			LOGGER.debug("Tour: {}, nights: {}.", entityFromDb.getId(), entityFromDb.getNights().toString());
		}

		final Integer maxNights = tourService.getMaxNights();
		LOGGER.debug("Max nights: {}.", maxNights.toString());

		assertEquals(maxNights, tourService.getMaxNights());

	}

}