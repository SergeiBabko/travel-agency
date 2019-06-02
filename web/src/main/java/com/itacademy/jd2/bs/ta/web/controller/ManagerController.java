package com.itacademy.jd2.bs.ta.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.IBookedService;
import com.itacademy.jd2.bs.ta.service.ICityService;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;
import com.itacademy.jd2.bs.ta.service.util.EmailSender;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.BookedToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CityToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CountryToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CustomerToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TourToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TourTypeToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.UserAccountToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.BookedDTO;
import com.itacademy.jd2.bs.ta.web.dto.CityDTO;
import com.itacademy.jd2.bs.ta.web.dto.CountryDTO;
import com.itacademy.jd2.bs.ta.web.dto.CustomerDTO;
import com.itacademy.jd2.bs.ta.web.dto.TourDTO;
import com.itacademy.jd2.bs.ta.web.dto.TourTypeDTO;
import com.itacademy.jd2.bs.ta.web.dto.UserAccountDTO;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController extends AbstractController<UserAccountDTO> {

	private IUserAccountService userAccountService;
	private ICustomerService customerService;
	private IFavoriteService favoriteService;
	private IBookedService bookedService;
	private ITourService tourService;
	private ITourTypeService tourTypeService;
	private ICountryService countryService;
	private ICityService cityService;

	private UserAccountToDTOConverter userAccountToDtoConverter;
	private CustomerToDTOConverter customerToDtoConverter;
	private BookedToDTOConverter bookedToDtoConverter;
	private TourToDTOConverter tourToDtoConverter;
	private TourTypeToDTOConverter tourTypeToDtoConverter;
	private CountryToDTOConverter countryToDtoConverter;
	private CityToDTOConverter cityToDtoConverter;
	private EmailSender emailSender;

	@Autowired
	public ManagerController(final IUserAccountService userAccountService, final ICustomerService customerService,
			final IFavoriteService favoriteService, final IBookedService bookedService, final ITourService tourService,
			final ITourTypeService tourTypeService, final ICountryService countryService,
			final ICityService cityService, final UserAccountToDTOConverter userAccountToDtoConverter,
			final CustomerToDTOConverter customerToDtoConverter, final BookedToDTOConverter bookedToDtoConverter,
			final TourToDTOConverter tourToDtoConverter, final TourTypeToDTOConverter tourTypeToDtoConverter,
			final CountryToDTOConverter countryToDtoConverter, final CityToDTOConverter cityToDtoConverter,
			final EmailSender emailSender) {
		super();
		this.userAccountService = userAccountService;
		this.customerService = customerService;
		this.favoriteService = favoriteService;
		this.bookedService = bookedService;
		this.tourService = tourService;
		this.tourTypeService = tourTypeService;
		this.countryService = countryService;
		this.cityService = cityService;
		this.userAccountToDtoConverter = userAccountToDtoConverter;
		this.customerToDtoConverter = customerToDtoConverter;
		this.bookedToDtoConverter = bookedToDtoConverter;
		this.tourToDtoConverter = tourToDtoConverter;
		this.tourTypeToDtoConverter = tourTypeToDtoConverter;
		this.countryToDtoConverter = countryToDtoConverter;
		this.cityToDtoConverter = cityToDtoConverter;
		this.emailSender = emailSender;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {

		final Map<String, Object> models = new HashMap<>();
		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.admin.name())
				|| getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.moderator.name())) {

			final IUserAccount user = userAccountService.getById(getLoggedUserId());
			UserAccountDTO userDTO = userAccountToDtoConverter.apply(user);

			models.put("user", userDTO);

			UserAccountFilter uFilter = new UserAccountFilter();
			uFilter.setSortColumn("id");
			uFilter.setSortOrder(true);
			final List<IUserAccount> userAccounts = userAccountService.find(uFilter);
			List<UserAccountDTO> uDtos = new ArrayList<>();
			for (IUserAccount userAccount : userAccounts) {
				if (userAccount.getRole().equals(UserRole.admin) || userAccount.getRole().equals(UserRole.moderator)) {
					uDtos.add(userAccountToDtoConverter.apply(userAccount));
				}
			}
			models.put("userAccounts", uDtos);

			CustomerFilter customerFilter = new CustomerFilter();
			uFilter.setSortColumn("id");
			uFilter.setSortOrder(true);
			final List<ICustomer> customers = customerService.find(customerFilter);
			List<CustomerDTO> cDtos = customers.stream().map(customerToDtoConverter).collect(Collectors.toList());
			models.put("customers", cDtos);

			BookedFilter bFilter = new BookedFilter();
			bFilter.setSortColumn("id");
			bFilter.setSortOrder(true);
			final List<IBooked> booked = bookedService.find(bFilter);
			List<BookedDTO> bDtos = booked.stream().map(bookedToDtoConverter).collect(Collectors.toList());
			models.put("booked", bDtos);

			TourFilter tFilter = new TourFilter();
			uFilter.setSortColumn("id");
			uFilter.setSortOrder(true);
			final List<ITour> tours = tourService.find(tFilter);
			List<TourDTO> tDtos = tours.stream().map(tourToDtoConverter).collect(Collectors.toList());
			for (TourDTO tourDTO : tDtos) {
				long favoriteCount = favoriteService.getCountByTourId(tourDTO.getId());
				long bookedCount = bookedService.getCountByTourId(tourDTO.getId());
				if (favoriteCount > 0) {
					tourDTO.setFavoriteCount(favoriteCount);
				}
				if (bookedCount > 0) {
					tourDTO.setBookedCount(bookedCount);
				}
			}
			models.put("tours", tDtos);

			final CityFilter filter = new CityFilter();
			filter.setSortColumn("name");
			filter.setSortColumn2("country");
			filter.setSortOrder(true);
			final List<ICity> cities = cityService.find(filter);
			List<CityDTO> cityDtos = cities.stream().map(cityToDtoConverter).collect(Collectors.toList());
			models.put("cities", cityDtos);

			final List<ICountry> countries = countryService.getAll();
			List<CountryDTO> countriesDtos = countries.stream().map(countryToDtoConverter).collect(Collectors.toList());
			models.put("countries", countriesDtos);

			final List<ITourType> tourTypes = tourTypeService.getAll();
			List<TourTypeDTO> tourTypesDtos = tourTypes.stream().map(tourTypeToDtoConverter)
					.collect(Collectors.toList());
			models.put("tourTypes", tourTypesDtos);

			return new ModelAndView("manager.list", models);
		}
		return new ModelAndView("manager.list", models);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> register(
			@RequestParam(name = "email", required = true) final String email,
			@RequestParam(name = "password", required = true) String password,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "surname", required = true) String surname) {

		final Map<String, Object> response = new HashMap<>();

		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.admin.name())) {
			if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password) && !StringUtils.isBlank(name)
					&& !StringUtils.isBlank(surname)) {
				if (userAccountService.validateEmail(email)) {
					if (password.replaceAll(" ", "").length() >= 6) {

						IUserAccount user = userAccountService.createEntity();
						user.setEmail(email);
						password = DigestUtils.md5Hex(password).toUpperCase();
						user.setPassword(password);
						user.setRole(UserRole.moderator);
						user.setFirstName(name);
						user.setLastName(surname);

						try {
							userAccountService.save(user);
							emailSender.sendEmail(user, "Welcome to Travel Agency!");
							long count = userAccountService.getCount();
							response.put("result", true);
							response.put("count", count);
							response.put("message", "Done!");
							response.put("userId", user.getId());
							response.put("userEmail", user.getEmail());
							response.put("userName", user.getFirstName());
							response.put("userSurname", user.getLastName());
							response.put("userRole", user.getRole().name());
							DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
							String date = df.format(user.getCreated());
							response.put("userCreated", date);

							return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
						} catch (NotUniqueUserException e) {
							response.put("result", false);
							response.put("message", "User account with this email is already exists.");
							return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
						} catch (Exception e) {
							response.put("result", false);
							response.put("message", "User account with this email is already exists.");
							return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
						}

					} else {
						response.put("result", false);
						response.put("message", "Password must be longer than 6 characters.");
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
					}
				} else {
					response.put("result", false);
					response.put("message", "You entered the wrong email address. Try again.");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
			} else {
				response.put("result", false);
				response.put("message",
						"You need to enter real email, password and full name to register new moderator.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		} else {
			response.put("result", false);
			response.put("message", "You don't have any permissions to add moderators!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> delete(@RequestParam(name = "id", required = true) final Integer id) {

		String result = null;
		if (id != null) {
			try {
				userAccountService.delete(id);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (Exception e) {
				result = "User account with this id doesn't exists.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "User account with this id doesn't exists.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/blockcustomer", method = RequestMethod.POST)
	public ResponseEntity<String> block(@RequestParam(name = "id", required = false) final Integer id) {
		String result = null;
		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {
			result = customerService.activateOrDeactivate(id);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			result = "You don't have any permissions to edit this content!";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestParam(name = "id", required = true) final Integer id,
			@RequestParam(name = "firstName", required = false) final String firstName,
			@RequestParam(name = "lastName", required = false) final String lastName) {

		boolean editUser = false;
		String result = null;

		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (getLoggedUserId() == id) {
				IUserAccount user = userAccountService.getById(getLoggedUserId());

				if (firstName != null) {
					user.setFirstName(firstName);
					editUser = true;
				}
				if (lastName != null) {
					user.setLastName(lastName);
					editUser = true;
				}

				try {
					if (editUser) {
						userAccountService.save(user);
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
