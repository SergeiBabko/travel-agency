package com.itacademy.jd2.bs.ta.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.IBookedService;
import com.itacademy.jd2.bs.ta.service.ICityService;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;
import com.itacademy.jd2.bs.ta.service.IMailService;
import com.itacademy.jd2.bs.ta.service.INewsService;
import com.itacademy.jd2.bs.ta.service.IReviewService;
import com.itacademy.jd2.bs.ta.service.ITaDescriptionService;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;

@SpringJUnitConfig(locations = "classpath:service-context-test.xml")
@Sql(scripts = "classpath:deleteDB.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractTest {

	@Autowired
	protected ICountryService countryService;
	@Autowired
	protected IBookedService bookedService;
	@Autowired
	protected ICityService cityService;
	@Autowired
	protected ICustomerService customerService;
	@Autowired
	protected IFavoriteService favoriteService;
	@Autowired
	protected INewsService newsService;
	@Autowired
	protected IReviewService reviewService;
	@Autowired
	protected ITaDescriptionService taDescriptionService;
	@Autowired
	protected ITourDateService tourDateService;
	@Autowired
	protected ITourService tourService;
	@Autowired
	protected ITourTypeService tourTypeService;
	@Autowired
	protected IUserAccountService userAccountService;
	@Autowired
	protected IMailService mailService;

	// ----------------------------------------------------------------------------------------------------

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

	private static final Random RANDOM = new Random();

	@BeforeAll
	public static void test() {
		LOGGER.debug("<----- Data Base Test Start ----->");
	}

	@AfterAll
	public static void cleanUp() {
		LOGGER.debug("<----- Clean-Up Data Base ----->");
		LOGGER.debug("<----- Data Base Test End ----->");
	}

	protected int getRandomPrefix() {
		int random = RANDOM.nextInt(999999 - 0 + 1) + 0;
		random -= RANDOM.nextInt(100 - 1 + 1) + 1;
		random -= RANDOM.nextInt(100 - 1 + 1) + 1;
		random /= RANDOM.nextInt(100 - 1 + 1) + 1;
		return random;
	}

	protected int getRandomCount() {
		return RANDOM.nextInt(10 - 1 + 1) + 1;
	}

	protected int verify() {
		return RANDOM.nextInt(1 - 0 + 1) + 0;
	}

	public Random getRANDOM() {
		return RANDOM;
	}

	// ----------------------------------------------------------------------------------------------------

	public IMailService getEmailSender(final Integer i) {
		final IMailService spy = Mockito.spy(mailService);
		switch (i) {
		case 1:
			Mockito.doAnswer(new Answer<Void>() {
				@Override
				public Void answer(final InvocationOnMock invocation) throws Throwable {
					LOGGER.info("email sending simulated: {}", invocation.getArguments()[1]);
					return null;
				}
			}).when(spy).sendEmail(any(IUserAccount.class), anyString());
			return spy;
		case 2:
			Mockito.doAnswer(new Answer<Void>() {
				@Override
				public Void answer(final InvocationOnMock invocation) throws Throwable {
					LOGGER.info("email sending simulated: {}, {}", invocation.getArguments()[1],
							invocation.getArguments()[2]);
					return null;
				}
			}).when(spy).sendEmail(any(IUserAccount.class), anyString(), anyString());
			return spy;
		default:
			break;
		}
		return spy;
	}

	// ----------------------------------------------------------------------------------------------------

	protected IBooked saveNewBooked() {
		final IBooked entity = bookedService.createEntity();
		final ICustomer customer = saveNewCustomer();
		final ITourDate tourDate = saveNewTourDate();

		entity.setCustomer(customer);
		entity.setTourDate(tourDate);

		if (verify() == 1) {
			entity.setNumPerson(getRandomCount());
			entity.setMessage("Some user text " + getRandomPrefix());
		}

		entity.setPrice(getRandomPrefix());
		entity.setProcessed(RANDOM.nextBoolean());
		bookedService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ICity saveNewCity() {
		final ICountry country = saveNewCountry();
		final ICity entity = cityService.createEntity();

		entity.setCountry(country);
		entity.setName("City:" + getRandomPrefix());
		cityService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ICountry saveNewCountry() {
		final ICountry entity = countryService.createEntity();

		entity.setName("Country:" + getRandomPrefix());
		countryService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ICustomer saveNewCustomer() {

		final IUserAccount userAccount = userAccountService.createEntity();

		userAccount.setEmail("Email" + getRandomPrefix());
		userAccount.setPassword("Password" + getRandomPrefix());

		if (verify() == 1) {
			userAccount.setFirstName("FirstName" + getRandomPrefix());
			userAccount.setLastName("LastName" + getRandomPrefix());
		}

		final ICustomer entity = customerService.createEntity();

		if (verify() == 1) {
			LOGGER.debug("Create customer with properties.");
			entity.setMiddleName("Middle_name:" + getRandomPrefix());
			entity.setBirthday("" + getRandomPrefix());
			entity.setPassportNumber("" + getRandomPrefix());
			entity.setPassportStart("" + getRandomPrefix());
			entity.setPassportEnd("" + getRandomPrefix());
			entity.setPhoneNumber("" + getRandomPrefix());
			entity.setCountry("Country: " + getRandomPrefix());
			entity.setCity("City: " + getRandomPrefix());
			entity.setStreet("Street: " + getRandomPrefix());
			entity.setBonus(getRandomCount());
		} else {
			LOGGER.debug("Create customer without properties.");
		}

		try {
			customerService.save(userAccount, entity);
		} catch (NotUniqueUserException e) {
			LOGGER.debug("Not unique user.");
		}

		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected IFavorite saveNewFavorite() {
		final IFavorite entity = favoriteService.createEntity();
		final ICustomer customer = saveNewCustomer();
		final ITour tour = saveNewTour();

		entity.setCustomer(customer);
		entity.setTour(tour);
		favoriteService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected INews saveNewNews() {
		final INews entity = newsService.createEntity();

		if (verify() == 1) {
			entity.setImage("Img url " + getRandomPrefix());
		}

		entity.setName("Name: " + getRandomPrefix());
		entity.setText("Some news text " + getRandomPrefix());
		newsService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected IReview saveNewReview() {
		final IReview entity = reviewService.createEntity();
		final ICustomer customer = saveNewCustomer();
		final ITourDate tourDate = saveNewTourDate();

		entity.setReview("Some user review " + getRandomPrefix());
		entity.setRating(getRandomCount());
		entity.setCustomer(customer);
		entity.setTourDate(tourDate);
		reviewService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ITaDescription saveNewTaDescription() {
		final ITaDescription entity = taDescriptionService.createEntity();

		entity.setDescription("Some Ta description " + getRandomPrefix());
		entity.setContacts("Some Ta contacts " + getRandomPrefix());
		entity.setAddress("Some Ta address " + getRandomPrefix());

		if (verify() == 1) {
			entity.setImage1("Img url " + getRandomPrefix());
			entity.setImage2("Img url " + getRandomPrefix());
			entity.setImage3("Img url " + getRandomPrefix());
		}

		taDescriptionService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ITourDate saveNewTourDate() {
		final ITourDate entity = tourDateService.createEntity();
		final ITour tour = saveNewTour();
		LocalDate date = LocalDate.now();

		entity.setTour(tour);
		entity.setNumPerson(getRandomCount());
		entity.setDateStart(getDateWithowtTime(date));
		date = date.plusDays(10);
		entity.setDateEnd(getDateWithowtTime(date));
		tourDateService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ITour saveNewTour() {
		final ITour entity = tourService.createEntity();
		final ITourType tourType = saveNewTourType();
		final ICity city = saveNewCity();

		entity.setTourStatus(TourStatus.active);
		entity.setName("Name" + getRandomPrefix());
		entity.setPrice(getRandomPrefix());
		entity.setTourType(tourType);
		entity.setNights(getRandomCount());
		entity.setDescription("Some tour description " + getRandomPrefix());
		entity.setCity(city);
		entity.setAddress("Some tour address " + getRandomPrefix());

		if (verify() == 1) {
			entity.setImage("Img url " + getRandomPrefix());
		}

		tourService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected ITourType saveNewTourType() {
		final ITourType entity = tourTypeService.createEntity();

		entity.setType("Type: " + getRandomPrefix());
		tourTypeService.save(entity);
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected IUserAccount saveNewUserAccount() {
		final IUserAccount entity = userAccountService.createEntity();

		final UserRole[] allRoles = UserRole.values();
		final int randomIndex = getRANDOM().nextInt(allRoles.length) + 0;
		entity.setRole(allRoles[randomIndex]);

		entity.setEmail("Email" + getRandomPrefix());
		entity.setPassword("Password" + getRandomPrefix());

		if (verify() == 1) {
			entity.setFirstName("FirstName" + getRandomPrefix());
			entity.setLastName("LastName" + getRandomPrefix());
		}

		try {
			userAccountService.save(entity);
			getEmailSender(1).sendEmail(entity, " Email Header");
		} catch (NotUniqueUserException e) {
			LOGGER.debug("Not unique user.");
		}
		return entity;
	}

	// ----------------------------------------------------------------------------------------------------

	protected Date getDateWithowtTime(final LocalDate localDate) {
		Date date = java.sql.Date.valueOf(localDate);
		return date;
	}

}