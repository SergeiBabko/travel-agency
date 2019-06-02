package com.itacademy.jd2.bs.ta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.IBookedService;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.ITaDescriptionService;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.BookedToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CustomerToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TaDescriptionToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TourToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.UserAccountToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.BookedDTO;
import com.itacademy.jd2.bs.ta.web.dto.CustomerDTO;
import com.itacademy.jd2.bs.ta.web.dto.TaDescriptionDTO;
import com.itacademy.jd2.bs.ta.web.dto.TourDTO;
import com.itacademy.jd2.bs.ta.web.dto.UserAccountDTO;

@Controller
@RequestMapping(value = "/booked")
public class BookedController extends AbstractController<BookedDTO> {

	private IBookedService bookedService;
	private IUserAccountService userAccountService;
	private ICustomerService customerService;
	private ITourService tourService;
	private ITourDateService tourDateService;
	private ITaDescriptionService taDescriptionService;

	private BookedToDTOConverter bookedToDtoConverter;
	private UserAccountToDTOConverter userAccountToDtoConverter;
	private CustomerToDTOConverter customerToDtoConverter;
	private TaDescriptionToDTOConverter taDescriptionToDtoConverter;
	private TourToDTOConverter tourToDtoConverter;

	@Autowired
	public BookedController(final IBookedService bookedService, final IUserAccountService userAccountService,
			final ICustomerService customerService, final ITourService tourService,
			final ITourDateService tourDateService, final ITaDescriptionService taDescriptionService,
			final BookedToDTOConverter bookedToDtoConverter, final UserAccountToDTOConverter userAccountToDtoConverter,
			final CustomerToDTOConverter customerToDtoConverter,
			final TaDescriptionToDTOConverter taDescriptionToDtoConverter,
			final TourToDTOConverter tourToDtoConverter) {
		super();
		this.bookedService = bookedService;
		this.userAccountService = userAccountService;
		this.customerService = customerService;
		this.tourService = tourService;
		this.tourDateService = tourDateService;
		this.taDescriptionService = taDescriptionService;
		this.bookedToDtoConverter = bookedToDtoConverter;
		this.userAccountToDtoConverter = userAccountToDtoConverter;
		this.customerToDtoConverter = customerToDtoConverter;
		this.taDescriptionToDtoConverter = taDescriptionToDtoConverter;
		this.tourToDtoConverter = tourToDtoConverter;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> add(
			@RequestParam(name = "tourDateId", required = true) final String receivedTourDateId,
			@RequestParam(name = "personCount", required = true) final String receivedPersonCount,
			@RequestParam(name = "tourId", required = true) final String receivedTourId,

			@RequestParam(name = "firstName", required = false) final String receivedFirstName,
			@RequestParam(name = "lastName", required = false) final String receivedLastName,
			@RequestParam(name = "middleName", required = false) final String receivedMiddleName,
			@RequestParam(name = "birthday", required = false) final String receivedBirthday,
			@RequestParam(name = "passportNumber", required = false) final String receivedPassportNumber,
			@RequestParam(name = "passportStart", required = false) final String receivedPassportStart,
			@RequestParam(name = "passportEnd", required = false) final String receivedPassportEnd,
			@RequestParam(name = "phoneNumber", required = false) final String receivedPhoneNumber,
			@RequestParam(name = "country", required = false) final String receivedCountry,
			@RequestParam(name = "city", required = false) final String receivedCity,
			@RequestParam(name = "street", required = false) final String receivedStreet,
			@RequestParam(name = "message", required = false) final String receivedMessage) {

		boolean exist = false;
		boolean editUser = false;
		boolean editCustomer = false;
		String result = null;

		Integer tourDateId = null;
		Integer personCount = null;
		Integer tourId = null;

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {

			IBooked entity = bookedService.createEntity();
			IUserAccount user = userAccountService.getById(getLoggedUserId());
			ICustomer customer = customerService.getById(getLoggedUserId());

			if (!StringUtils.isBlank(receivedTourDateId) && !StringUtils.isBlank(receivedPersonCount)
					&& !StringUtils.isBlank(receivedTourId)) {
				tourDateId = Integer.valueOf(receivedTourDateId);
				personCount = Integer.valueOf(receivedPersonCount);
				tourId = Integer.valueOf(receivedTourId);

				ITourDate tourDate = tourDateService.getById(tourDateId);
				ITour tour = tourService.getById(tourId);

				Integer price = tour.getPrice();
				if (customer.getBonus() != null) {
					price = price - price * customer.getBonus() / 100;
				}

				entity.setTourDate(tourDate);
				entity.setNumPerson(personCount);
				entity.setCustomer(customer);
				entity.setPrice(price);
				entity.setProcessed(false);

				exist = true;
			}
			if (!StringUtils.isBlank(receivedMessage)) {
				entity.setMessage(receivedMessage);
			}
			if (!StringUtils.isBlank(receivedFirstName)
					&& (user.getFirstName() == null || StringUtils.isBlank(user.getFirstName()))) {
				user.setFirstName(receivedFirstName);
				editUser = true;
			}
			if (!StringUtils.isBlank(receivedLastName)
					&& (user.getLastName() == null || StringUtils.isBlank(user.getLastName()))) {
				user.setLastName(receivedLastName);
				editUser = true;
			}
			if (!StringUtils.isBlank(receivedMiddleName)
					&& (customer.getMiddleName() == null || StringUtils.isBlank(customer.getMiddleName()))) {
				customer.setMiddleName(receivedMiddleName);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedBirthday)
					&& (customer.getBirthday() == null || StringUtils.isBlank(customer.getBirthday()))) {
				customer.setBirthday(receivedBirthday);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedPassportNumber)
					&& (customer.getPassportNumber() == null || StringUtils.isBlank(customer.getPassportNumber()))) {
				customer.setPassportNumber(receivedPassportNumber);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedPassportStart)
					&& (customer.getPassportStart() == null || StringUtils.isBlank(customer.getPassportStart()))) {
				customer.setPassportStart(receivedPassportStart);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedPassportEnd)
					&& (customer.getPassportEnd() == null || StringUtils.isBlank(customer.getPassportEnd()))) {
				customer.setPassportEnd(receivedPassportEnd);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedPhoneNumber)
					&& (customer.getPhoneNumber() == null || StringUtils.isBlank(customer.getPhoneNumber()))) {
				customer.setPhoneNumber(receivedPhoneNumber);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedCountry)
					&& (customer.getCountry() == null || StringUtils.isBlank(customer.getCountry()))) {
				customer.setCountry(receivedCountry);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedCity)
					&& (customer.getCity() == null || StringUtils.isBlank(customer.getCity()))) {
				customer.setCity(receivedCity);
				editCustomer = true;
			}
			if (!StringUtils.isBlank(receivedStreet)
					&& (customer.getStreet() == null || StringUtils.isBlank(customer.getStreet()))) {
				customer.setStreet(receivedStreet);
				editCustomer = true;
			}

			if (editUser) {
				try {
					userAccountService.save(user);
				} catch (NotUniqueUserException e) {
					result = "Not unique user.";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}

			if (editCustomer) {
				customerService.update(customer);
			}

			if (exist) {
				bookedService.save(entity);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				result = "Сheck the data you entered is correct.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}

		} else {
			result = "You don't have any permissions to edit this content!";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResponseEntity<String> delete(@RequestParam(name = "bookedId", required = true) final Integer bookedId) {

		String result = null;
		boolean exist = false;

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			final BookedFilter filter = new BookedFilter();
			final List<IBooked> entities = bookedService.getByCustomerId(filter, getLoggedUserId());

			for (IBooked booked : entities) {
				if (booked.getId() == bookedId) {
					exist = true;
					break;
				} else {
					exist = false;
				}
			}

			if (exist) {
				bookedService.delete(bookedId);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				result = "This application doesn't exist!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.admin.name())
				|| getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.moderator.name())) {

			try {
				final IBooked entity = bookedService.getById(bookedId);
				if (entity != null) {
					exist = true;
				}
			} catch (Exception e) {
				exist = false;
			}

			if (exist) {
				try {
					bookedService.delete(bookedId);
					result = "Done!";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				} catch (Exception e) {
					result = "Something went wrong.";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			} else {
				result = "This application doesn't exist!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "You don't have any permissions to edit this content!";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/processed", method = RequestMethod.POST)
	public ResponseEntity<String> processed(@RequestParam(name = "bookedId", required = true) final Integer bookedId) {

		String result = null;
		boolean exist = false;

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.admin.name())
				|| getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.moderator.name())) {

			try {
				IBooked entity = bookedService.getById(bookedId);
				if (entity != null) {
					exist = true;
				}
			} catch (Exception e) {
				exist = false;
			}

			if (exist) {
				IBooked entity = bookedService.getById(bookedId);
				ICustomer customer = customerService.getById(entity.getCustomer().getId());
				final Integer customerBonus = customer.getBonus();
				try {
					if (entity.getProcessed().equals(false)) {
						entity.setProcessed(true);
						bookedService.save(entity);
						if (customerBonus != null && customerBonus <= 10) {
							customer.setBonus(customerBonus + 5);
							customerService.update(customer);
						} else if (customerBonus == null) {
							customer.setBonus(5);
							customerService.update(customer);
						}
						result = "Set as processed!";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					} else {
						entity.setProcessed(false);
						bookedService.save(entity);
						if (customerBonus != null && customerBonus >= 5) {
							customer.setBonus(customerBonus - 5);
							customerService.update(customer);
						}
						result = "Set as unprocessed!";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					}
				} catch (Exception e) {
					result = "Something went wrong.";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			} else {
				result = "This application doesn't exist!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "You don't have any permissions to edit this content!";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
		BookedDTO booked = bookedToDtoConverter.apply(bookedService.getById(id));
		CustomerDTO customer = customerToDtoConverter.apply(customerService.getById(booked.getCustomerId()));
		UserAccountDTO user = userAccountToDtoConverter.apply(userAccountService.getById(customer.getId()));
		TourDTO tour = tourToDtoConverter.apply(tourService.getById(booked.getTourId()));

		List<TaDescriptionDTO> description = taDescriptionService.getAll().stream().map(taDescriptionToDtoConverter)
				.collect(Collectors.toList());

		final Map<String, Object> hashMap = new HashMap<>();

		hashMap.put("booked", booked);
		hashMap.put("customer", customer);
		hashMap.put("user", user);
		hashMap.put("description", description);
		hashMap.put("tour", tour);

		return new ModelAndView("booked", hashMap);
	}

	@RequestMapping(value = "/getcountbymonth", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Long>> getCountByMonth() {
		final Map<Integer, Long> map = bookedService.getBookedByMonth();
		final Map<String, Long> response = new HashMap<>();
		response.put("january", map.get(1));
		response.put("february", map.get(2));
		response.put("march", map.get(3));
		response.put("april", map.get(4));
		response.put("may", map.get(5));
		response.put("june", map.get(6));
		response.put("july", map.get(7));
		response.put("august", map.get(8));
		response.put("september", map.get(9));
		response.put("october", map.get(10));
		response.put("november", map.get(11));
		response.put("december", map.get(12));
		return new ResponseEntity<Map<String, Long>>(response, HttpStatus.OK);
	}

}
