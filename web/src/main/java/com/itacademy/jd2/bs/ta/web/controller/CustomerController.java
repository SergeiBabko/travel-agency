package com.itacademy.jd2.bs.ta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;
import com.itacademy.jd2.bs.ta.service.IBookedService;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;
import com.itacademy.jd2.bs.ta.service.IReviewService;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.BookedToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CustomerToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.FavoriteToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.ReviewToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.UserAccountToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.BookedDTO;
import com.itacademy.jd2.bs.ta.web.dto.CustomerDTO;
import com.itacademy.jd2.bs.ta.web.dto.FavoriteDTO;
import com.itacademy.jd2.bs.ta.web.dto.ReviewDTO;
import com.itacademy.jd2.bs.ta.web.dto.UserAccountDTO;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends AbstractController<CustomerDTO> {

	private ICustomerService customerService;
	private IUserAccountService userAccountService;
	private IFavoriteService favoriteService;
	private IBookedService bookedService;
	private IReviewService reviewService;

	private CustomerToDTOConverter toDtoConverter;
	private UserAccountToDTOConverter userAccountToDTOConverter;
	private FavoriteToDTOConverter favoriteToDtoConverter;
	private BookedToDTOConverter bookedToDtoConverter;
	private ReviewToDTOConverter reviewToDtoConverter;

	@Autowired
	public CustomerController(final ICustomerService customerService, final IUserAccountService userAccountService,
			final IFavoriteService favoriteService, final IBookedService bookedService,
			final IReviewService reviewService, final CustomerToDTOConverter toDtoConverter,
			final UserAccountToDTOConverter userAccountToDTOConverter,
			final FavoriteToDTOConverter favoriteToDtoConverter, final BookedToDTOConverter bookedToDtoConverter,
			final ReviewToDTOConverter reviewToDtoConverter) {
		super();
		this.customerService = customerService;
		this.userAccountService = userAccountService;
		this.favoriteService = favoriteService;
		this.bookedService = bookedService;
		this.reviewService = reviewService;
		this.toDtoConverter = toDtoConverter;
		this.userAccountToDTOConverter = userAccountToDTOConverter;
		this.favoriteToDtoConverter = favoriteToDtoConverter;
		this.bookedToDtoConverter = bookedToDtoConverter;
		this.reviewToDtoConverter = reviewToDtoConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {

		Integer id = null;
		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			id = getLoggedUserId();
		}

		final Map<String, Object> models = new HashMap<>();

		final ICustomer customer = customerService.getById(id);
		CustomerDTO customerDTO = toDtoConverter.apply(customer);

		final IUserAccount user = userAccountService.getById(id);
		UserAccountDTO userDTO = userAccountToDTOConverter.apply(user);

		models.put("customer", customerDTO);
		models.put("user", userDTO);

		FavoriteFilter fFilter = new FavoriteFilter();
		fFilter.setSortColumn("id");
		fFilter.setSortOrder(false);
		try {
			List<IFavorite> favorites = favoriteService.getByCustomerId(getLoggedUserId(), fFilter);
			List<FavoriteDTO> favoritesDTOs = favorites.stream().map(favoriteToDtoConverter)
					.collect(Collectors.toList());
			models.put("favorites", favoritesDTOs);
		} catch (Exception e) {
			models.put("favorites", null);
		}

		BookedFilter bFilter = new BookedFilter();
		bFilter.setSortColumn("created");
		bFilter.setSortOrder(false);
		try {
			final List<IBooked> booked = bookedService.getByCustomerId(bFilter, getLoggedUserId());
			List<BookedDTO> bookedDTOs = booked.stream().map(bookedToDtoConverter).collect(Collectors.toList());
			models.put("booked", bookedDTOs);
		} catch (Exception e) {
			models.put("booked", null);
		}

		ReviewFilter rFilter = new ReviewFilter();
		rFilter.setSortColumn("id");
		rFilter.setSortOrder(false);
		try {
			final List<IReview> reviews = reviewService.getByCustomerId(rFilter, getLoggedUserId());
			List<ReviewDTO> reviewsDTOs = reviews.stream().map(reviewToDtoConverter).collect(Collectors.toList());
			models.put("review", reviewsDTOs);
		} catch (Exception e) {
			models.put("review", null);
		}

		return new ModelAndView("customer.list", models);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResponseEntity<String> delete(
			@RequestParam(name = "customerId", required = false) final Integer customerId) {
		String result = null;
		Integer id = null;
		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			id = getLoggedUserId();
			if (customerId == id) {
				customerService.delete(id);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				result = "You can't delete this account.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "Unable to identify customer account.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestParam(name = "customerId", required = true) final Integer customerId,

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
			@RequestParam(name = "street", required = false) final String receivedStreet) {

		boolean editUser = false;
		boolean editCustomer = false;
		String result = null;

		Integer id = null;
		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			id = getLoggedUserId();
			if (customerId == id) {

				IUserAccount user = userAccountService.getById(getLoggedUserId());
				ICustomer customer = customerService.getById(getLoggedUserId());

				if (receivedFirstName != null) {
					user.setFirstName(receivedFirstName);
					editUser = true;
				}
				if (receivedLastName != null) {
					user.setLastName(receivedLastName);
					editUser = true;
				}
				if (receivedMiddleName != null) {
					customer.setMiddleName(receivedMiddleName);
					editCustomer = true;
				}
				if (receivedBirthday != null) {
					customer.setBirthday(receivedBirthday);
					editCustomer = true;
				}
				if (receivedPassportNumber != null) {
					customer.setPassportNumber(receivedPassportNumber);
					editCustomer = true;
				}
				if (receivedPassportStart != null) {
					customer.setPassportStart(receivedPassportStart);
					editCustomer = true;
				}
				if (receivedPassportEnd != null) {
					customer.setPassportEnd(receivedPassportEnd);
					editCustomer = true;
				}
				if (receivedPhoneNumber != null) {
					customer.setPhoneNumber(receivedPhoneNumber);
					editCustomer = true;
				}
				if (receivedCountry != null) {
					customer.setCountry(receivedCountry);
					editCustomer = true;
				}
				if (receivedCity != null) {
					customer.setCity(receivedCity);
					editCustomer = true;
				}
				if (receivedStreet != null) {
					customer.setStreet(receivedStreet);
					editCustomer = true;
				}

				try {
					if (editUser) {
						userAccountService.save(user);
					}
					if (editCustomer) {
						customerService.update(customer);
					}
					result = "Done!";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				} catch (Exception e) {
					result = "Unexpected error.";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}

			} else {
				result = "You can't manage this account.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}

		} else {
			result = "You can't manage this account.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

}
