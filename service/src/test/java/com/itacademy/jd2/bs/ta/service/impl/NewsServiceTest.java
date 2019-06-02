package com.itacademy.jd2.bs.ta.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.filter.NewsFilter;

public class NewsServiceTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewsServiceTest.class);

	@Test
	public void testCreate() {

		LOGGER.debug("testCreate:");

		final INews entity = saveNewNews();
		final INews entityFromDb = newsService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(entity.getName(), entityFromDb.getName());
		assertEquals(entity.getImage(), entityFromDb.getImage());
		assertEquals(entity.getText(), entityFromDb.getText());
		assertNotNull(entityFromDb.getName());
		assertNotNull(entityFromDb.getText());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

	}

	@Test
	public void testUpdate() throws InterruptedException {

		LOGGER.debug("testUpdate:");

		final INews entity = saveNewNews();

		String newName = entity.getName() + "_updated";
		String newImage = entity.getImage() + "_updated";
		String newText = entity.getText() + "_updated";

		entity.setName(newName);
		entity.setImage(newImage);
		entity.setText(newText);

		Thread.sleep(1000);
		newsService.save(entity);

		final INews entityFromDb = newsService.getById(entity.getId());

		assertNotNull(entityFromDb);
		assertEquals(newName, entityFromDb.getName());
		assertEquals(newImage, entityFromDb.getImage());
		assertEquals(newText, entityFromDb.getText());

		assertNotNull(entityFromDb.getName());
		assertNotNull(entityFromDb.getText());
		assertNotNull(entityFromDb.getImage());
		assertNotNull(entityFromDb.getId());
		assertNotNull(entityFromDb.getCreated());
		assertNotNull(entityFromDb.getUpdated());
		assertEquals(entity.getCreated(), entityFromDb.getCreated());
		assertTrue(entityFromDb.getUpdated().after(entity.getCreated()));

	}

	@Test
	public void testGetAll() {

		LOGGER.debug("testGetAll:");

		final int initialCount = newsService.getAll().size();
		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewNews();
		}

		final List<INews> allEntities = newsService.getAll();

		for (final INews entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getName());
			assertNotNull(entityFromDb.getText());
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

		final INews entity = saveNewNews();

		newsService.delete(entity.getId());
		assertNull(newsService.getById(entity.getId()));

	}

	@Test
	public void testDeleteAll() {

		LOGGER.debug("testDeleteAll:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewNews();
		}

		newsService.deleteAll();
		assertEquals(0, newsService.getAll().size());

	}

	@Test
	public void testGetCount() {

		LOGGER.debug("testGetCount:");

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewNews();
		}

		long count = newsService.getCount();

		LOGGER.debug("Count= " + count);

		assertEquals(randomObjectsCount, count);

	}

	@Test
	public void testFind() {

		LOGGER.debug("testFind:");

		final NewsFilter filter = new NewsFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("id");
		final int initialCount1 = newsService.find(filter).size();
		filter.setSortColumn("created");
		final int initialCount2 = newsService.find(filter).size();
		filter.setSortColumn("updated");
		final int initialCount3 = newsService.find(filter).size();
		filter.setSortColumn("name");
		final int initialCount4 = newsService.find(filter).size();

		final int randomObjectsCount = getRandomCount();

		for (int i = 0; i < randomObjectsCount; i++) {
			saveNewNews();
		}

		final List<INews> allEntities = newsService.find(filter);

		for (final INews entityFromDb : allEntities) {
			assertNotNull(entityFromDb);
			assertNotNull(entityFromDb.getName());
			assertNotNull(entityFromDb.getText());
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

}