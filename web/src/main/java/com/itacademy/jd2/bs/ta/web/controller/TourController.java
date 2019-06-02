package com.itacademy.jd2.bs.ta.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.CountryFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;
import com.itacademy.jd2.bs.ta.service.ICityService;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;
import com.itacademy.jd2.bs.ta.service.IReviewService;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;
import com.itacademy.jd2.bs.ta.web.converter.fromDTO.TourFromDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CityToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.CountryToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.ReviewToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TourDateToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TourToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.CityDTO;
import com.itacademy.jd2.bs.ta.web.dto.CountryDTO;
import com.itacademy.jd2.bs.ta.web.dto.ReviewDTO;
import com.itacademy.jd2.bs.ta.web.dto.TourDTO;
import com.itacademy.jd2.bs.ta.web.dto.TourDateDTO;
import com.itacademy.jd2.bs.ta.web.dto.list.GridStateDTO;
import com.itacademy.jd2.bs.ta.web.dto.search.TourSearchDTO;
import com.itacademy.jd2.bs.ta.web.tag.I18N;

@Controller
@RequestMapping(value = "/tours")
public class TourController extends AbstractController<TourDTO> {

	private ITourService tourService;
	private ITourDateService tourDateService;
	private ITourTypeService tourTypeService;
	private ICountryService countryService;
	private ICityService cityService;
	private IFavoriteService favoriteService;
	private ICustomerService customerService;
	private IReviewService reviewService;

	private TourToDTOConverter toDtoConverter;
	private TourFromDTOConverter fromDtoConverter;
	private TourDateToDTOConverter tourDateToDtoConverter;
	private CountryToDTOConverter countryToDtoConverter;
	private CityToDTOConverter cityToDtoConverter;
	private ReviewToDTOConverter reviewToDtoConverter;

	private static final String SEARCH_FORM = "searchForm";
	private static final String SEARCH_DTO = TourController.class.getSimpleName() + "_SEACH_DTO";

	@Autowired
	public TourController(final ITourService tourService, final ITourDateService tourDateService,
			final ITourTypeService tourTypeService, final ICountryService countryService,
			final ICityService cityService, final IFavoriteService favoriteService,
			final ICustomerService customerService, final IReviewService reviewService,
			final TourToDTOConverter toDtoConverter, final TourFromDTOConverter fromDtoConverter,
			final TourDateToDTOConverter tourDateToDtoConverter, final CountryToDTOConverter countryToDtoConverter,
			final CityToDTOConverter cityToDtoConverter, final ReviewToDTOConverter reviewToDtoConverter) {
		super();
		this.tourService = tourService;
		this.tourDateService = tourDateService;
		this.tourTypeService = tourTypeService;
		this.countryService = countryService;
		this.cityService = cityService;
		this.favoriteService = favoriteService;
		this.customerService = customerService;
		this.reviewService = reviewService;
		this.toDtoConverter = toDtoConverter;
		this.fromDtoConverter = fromDtoConverter;
		this.tourDateToDtoConverter = tourDateToDtoConverter;
		this.countryToDtoConverter = countryToDtoConverter;
		this.cityToDtoConverter = cityToDtoConverter;
		this.reviewToDtoConverter = reviewToDtoConverter;
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest req, @ModelAttribute(SEARCH_FORM) TourSearchDTO searchDto,
			@RequestParam(name = "page", required = false) final Integer pageNumber,
			@RequestParam(name = "sort", required = false) final String sortColumn) {
		final GridStateDTO gridState = getListDTO(req, 8);
		gridState.setPage(pageNumber);
		gridState.setSort(sortColumn, "id");

		if (req.getMethod().equalsIgnoreCase("get")) {
			searchDto = getSearchDTO(req);
		} else {
			req.getSession().setAttribute(SEARCH_DTO, searchDto);
		}

		final TourFilter filter = new TourFilter();
		final FavoriteFilter fFilter = new FavoriteFilter();

		if (searchDto.getCountryId() != null && !searchDto.getCountryId().equalsIgnoreCase("null")
				&& !searchDto.getCountryId().equalsIgnoreCase("")) {
			filter.setCountryId(Integer.valueOf(searchDto.getCountryId()));
		}
		if (searchDto.getCityId() != null && !searchDto.getCityId().equalsIgnoreCase("null")
				&& !searchDto.getCityId().equalsIgnoreCase("")) {
			filter.setCityId(Integer.valueOf(searchDto.getCityId()));
		}
		if (searchDto.getTourTypeId() != null && !searchDto.getTourTypeId().equalsIgnoreCase("null")
				&& !searchDto.getTourTypeId().equalsIgnoreCase("")) {
			filter.setTourTypeId(Integer.valueOf(searchDto.getTourTypeId()));
		}
		if (searchDto.getNights() != null) {
			String[] nightsStringRange = searchDto.getNights().split(";");

			int[] nightsInt = new int[nightsStringRange.length];

			for (int i = 0; i < nightsInt.length; i++) {
				nightsInt[i] = Integer.parseInt(nightsStringRange[i]);
			}

			final int minNights = nightsInt[0];
			final int maxNights = nightsInt[1];

			filter.setNightsMin(minNights);
			filter.setNightsMax(maxNights);
		}
		if (searchDto.getPrice() != null) {
			String[] priceStringRange = searchDto.getPrice().split(";");

			int[] priceInt = new int[priceStringRange.length];

			for (int i = 0; i < priceInt.length; i++) {
				priceInt[i] = Integer.parseInt(priceStringRange[i]);
			}

			final int minPrice = priceInt[0];
			final int maxPrice = priceInt[1];

			filter.setPriceMin(minPrice);
			filter.setPriceMax(maxPrice);
		}

		prepareFilter(gridState, filter);

		final List<ITour> entities = tourService.find(filter);
		List<TourDTO> dtos = entities.stream().map(toDtoConverter).collect(Collectors.toList());
		for (TourDTO tourDTO : dtos) {
			long favoriteCount = favoriteService.getCountByTourId(tourDTO.getId());
			if (favoriteCount > 0) {
				tourDTO.setFavoriteCount(favoriteCount);
			}
		}

		final Calendar now = Calendar.getInstance();
		Calendar tourUpdated = Calendar.getInstance();
		long diff = 0;

		for (TourDTO dto : dtos) {
			tourUpdated.setTime(dto.getUpdated());
			diff = TimeUnit.MILLISECONDS.toDays(Math.abs(now.getTimeInMillis() - tourUpdated.getTimeInMillis()));
			if (diff < 7) {
				dto.setNew(true);
			}
		}

		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			List<IFavorite> favorites = favoriteService.getByCustomerId(getLoggedUserId(), fFilter);
			for (IFavorite favorite : favorites) {
				Integer favoriteId = favorite.getTour().getId();
				for (TourDTO dto : dtos) {
					Integer dtoId = dto.getId();
					if (favoriteId == dtoId) {
						dto.setFavorite(true);
					}
				}
			}
		}

		gridState.setTotalCount(tourService.getCount());

		final Map<String, Object> models = new HashMap<>();
		models.put("gridItems", dtos);
		models.put("maxPrice", tourService.getMaxPrice());
		models.put("maxNights", tourService.getMaxNights());
		models.put(SEARCH_FORM, searchDto);

		if (filter.getNightsMin() != null) {
			models.put("fromNights", filter.getNightsMin());
		} else {
			models.put("fromNights", 0);
		}
		if (filter.getNightsMax() != null) {
			models.put("toNights", filter.getNightsMax());
		} else {
			models.put("toNights", tourService.getMaxNights());
		}

		if (filter.getPriceMin() != null) {
			models.put("fromPrice", filter.getPriceMin());
		} else {
			models.put("fromPrice", 0);
		}
		if (filter.getPriceMax() != null) {
			models.put("toPrice", filter.getPriceMax());
		} else {
			models.put("toPrice", tourService.getMaxPrice());
		}

		final List<ITourType> tourType = tourTypeService.getAll();
		final Map<Integer, String> tourTypeMap = tourType.stream()
				.collect(Collectors.toMap(ITourType::getId, ITourType::getType));

		final Object locale = req.getSession().getAttribute(I18N.SESSION_LOCALE_KEY);
		if (locale != null) {
			models.put("locale", locale);
		} else {
			models.put("locale", "en");
		}

		getCustomerBonus(userRole, models);
		getCountries(models);
		getCities(models, null);
		models.put("tourTypeChoice", tourTypeMap);
		return new ModelAndView("tour.list", models);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object save(@Valid @ModelAttribute("formModel") final TourDTO formModel, final BindingResult result) {
		if (result.hasErrors()) {
			final Map<String, Object> hashMap = new HashMap<>();
			hashMap.put("formModel", formModel);
			loadStatus(hashMap);
			getTourTypes(hashMap);
			return new ModelAndView("tour.edit", hashMap);
		} else {
			final ITour entity = fromDtoConverter.apply(formModel);
			tourService.save(entity);
			if (formModel.getId() != null) {
				return "redirect:/tours/" + formModel.getId();
			} else {
				return "redirect:/tours";
			}
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView showForm() {
		final Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("formModel", new TourDTO());
		loadStatus(hashMap);
		getTourTypes(hashMap);
		getCountries(hashMap);
		getCities(hashMap, null);
		return new ModelAndView("tour.edit", hashMap);
	}

	@RequestMapping(value = "/deleteoractivate", method = RequestMethod.GET)
	public ResponseEntity<Boolean> deleteoractivate(@RequestParam(name = "id", required = true) final Integer id) {
		Boolean bool = false;

		tourService.delete(id);

		final ITour tour = tourService.getById(id);

		if (tour.getTourStatus().equals(TourStatus.inactive)) {
			return new ResponseEntity<Boolean>(bool, HttpStatus.OK);
		} else {
			bool = true;
			return new ResponseEntity<Boolean>(bool, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
		final TourDTO dto = toDtoConverter.apply(tourService.getById(id));
		final Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("formModel", dto);
		loadStatus(hashMap);
		getTourTypes(hashMap);
		getCountries(hashMap);
		getCities(hashMap, dto.getCountryId());
		return new ModelAndView("tour.edit", hashMap);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView viewDetails(final HttpServletRequest req,
			@PathVariable(name = "id", required = true) final Integer id) {
		final TourDTO dto = toDtoConverter.apply(tourService.getById(id));
		long favoriteCount = favoriteService.getCountByTourId(dto.getId());
		if (favoriteCount > 0) {
			dto.setFavoriteCount(favoriteCount);
		}

		final FavoriteFilter fFilter = new FavoriteFilter();

		final Calendar now = Calendar.getInstance();
		Calendar tourUpdated = Calendar.getInstance();
		long diff = 0;

		tourUpdated.setTime(dto.getUpdated());
		diff = TimeUnit.MILLISECONDS.toDays(Math.abs(now.getTimeInMillis() - tourUpdated.getTimeInMillis()));
		if (diff <= 10) {
			dto.setNew(true);
		}

		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			List<IFavorite> favorites = favoriteService.getByCustomerId(getLoggedUserId(), fFilter);
			for (IFavorite favorite : favorites) {
				Integer favoriteId = favorite.getTour().getId();
				Integer dtoId = dto.getId();
				if (favoriteId == dtoId) {
					dto.setFavorite(true);
				}
			}
		}

		final TourDateFilter filter = new TourDateFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("dateStart");
		final List<ITourDate> entities = tourDateService.getByTourId(filter, id);
		List<TourDateDTO> tourDatesDTOs = new ArrayList<>();

		if (entities.size() != 0) {
			tourDatesDTOs = entities.stream().map(tourDateToDtoConverter).collect(Collectors.toList());
		}

		final ReviewFilter rFilter = new ReviewFilter();
		rFilter.setSortColumn("created");
		rFilter.setSortOrder(false);
		List<IReview> reviews = reviewService.getByTourId(rFilter, id);
		List<ReviewDTO> reviewDTOs = reviews.stream().map(reviewToDtoConverter).collect(Collectors.toList());

		final Map<String, Object> hashMap = new HashMap<>();

		final Object locale = req.getSession().getAttribute(I18N.SESSION_LOCALE_KEY);
		if (locale != null) {
			hashMap.put("locale", locale);
		} else {
			hashMap.put("locale", "en");
		}

		hashMap.put("formModel", dto);
		hashMap.put("tourDates", tourDatesDTOs);
		hashMap.put("tourReviews", reviewDTOs);
		hashMap.put("tourName", dto.getName());
		hashMap.put("userRole", userRole);
		loadStatus(hashMap);
		getCustomerBonus(userRole, hashMap);
		getUserInfo(hashMap);
		return new ModelAndView("tour.tourpage", hashMap);
	}

	private void loadStatus(final Map<String, Object> hashMap) {
		final List<TourStatus> tourStatusList = Arrays.asList(TourStatus.values());
		final Map<String, String> tourStatusMap = tourStatusList.stream()
				.collect(Collectors.toMap(TourStatus::name, TourStatus::name));
		hashMap.put("typeStatusChoice", tourStatusMap);
	}

	private void getTourTypes(final Map<String, Object> hashMap) {
		final List<ITourType> tourType = tourTypeService.getAll();
		final Map<Integer, String> tourTypeMap = tourType.stream()
				.collect(Collectors.toMap(ITourType::getId, ITourType::getType));
		hashMap.put("tourTypeChoice", tourTypeMap);
	}

	private void getCustomerBonus(final String userRole, final Map<String, Object> models) {
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			final Integer customerBonus = customerService.getById(getLoggedUserId()).getBonus();
			if (customerBonus != null) {
				models.put("customerBonus", customerBonus);
			} else {
				models.put("customerBonus", 0);
			}
		} else {
			models.put("customerBonus", 0);
		}
	}

	private void getUserInfo(final Map<String, Object> hashMap) {
		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			final int id = getLoggedUserId();
			final ICustomer customer = customerService.getById(id);
			hashMap.put("userAccount", customer.getUserAccount());
			hashMap.put("customerAccount", customer);
		}

	}

	private TourSearchDTO getSearchDTO(final HttpServletRequest req) {
		TourSearchDTO searchDTO = (TourSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
		if (searchDTO == null) {
			searchDTO = new TourSearchDTO();
			req.getSession().setAttribute(SEARCH_DTO, searchDTO);
		}
		return searchDTO;
	}

	private void getCountries(final Map<String, Object> hashMap) {
		CountryFilter countryFilter = new CountryFilter();
		countryFilter.setSortColumn("name");
		countryFilter.setSortOrder(true);
		final List<ICountry> countries = countryService.find(countryFilter);
		final Map<Integer, String> countryMap = countries.stream()
				.collect(Collectors.toMap(ICountry::getId, ICountry::getName));
		hashMap.put("countryChoice", countryMap);
	}

	private void getCities(final Map<String, Object> hashMap, final Integer countryId) {
		final CityFilter cityFilter = new CityFilter();
		cityFilter.setSortColumn("name");
		cityFilter.setSortOrder(true);
		if (countryId == null) {
			final List<ICity> cities = cityService.find(cityFilter);
			final Map<Integer, String> cityMap = cities.stream()
					.collect(Collectors.toMap(ICity::getId, ICity::getName));
			hashMap.put("cityChoice", cityMap);
		} else {
			final List<ICity> cities = cityService.find(cityFilter);
			Map<Integer, String> cityMap = new HashMap<>();
			for (ICity city : cities) {
				if (countryId == city.getCountry().getId()) {
					cityMap.put(city.getId(), city.getName());
				}
			}
			hashMap.put("cityChoice", cityMap);
		}
	}

	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	public ResponseEntity<List<CountryDTO>> getCountries() {

		CountryFilter countryFilter = new CountryFilter();
		countryFilter.setSortColumn("name");
		countryFilter.setSortOrder(true);
		final List<ICountry> countries = countryService.find(countryFilter);
		List<CountryDTO> countriesDto = countries.stream().map(countryToDtoConverter).collect(Collectors.toList());

		return new ResponseEntity<List<CountryDTO>>(countriesDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public ResponseEntity<List<CityDTO>> getCities(
			@RequestParam(name = "countryId", required = true) final Integer countryId) {

		final CityFilter cityFilter = new CityFilter();
		cityFilter.setSortColumn("name");
		cityFilter.setSortOrder(true);
		final List<ICity> c = cityService.find(cityFilter);
		List<CityDTO> citiesDto = c.stream().map(cityToDtoConverter).collect(Collectors.toList());
		final List<CityDTO> cities = new ArrayList<>();

		for (CityDTO cityDTO : citiesDto) {
			if (countryId == cityDTO.getCountryId()) {
				cities.add(cityDTO);
			}
		}

		return new ResponseEntity<List<CityDTO>>(cities, HttpStatus.OK);
	}

	@RequestMapping(value = "/tourdates", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Integer>> getTourDates(
			@RequestParam(name = "tourId", required = true) final Integer tourId) {

		final TourDateFilter filter = new TourDateFilter();
		filter.setSortOrder(true);
		filter.setSortColumn("dateStart");

		List<TourDateDTO> tourDates = tourDateService.getByTourId(filter, tourId).stream().map(tourDateToDtoConverter)
				.collect(Collectors.toList());

		Map<String, Integer> tourDateMap = new LinkedHashMap<>();

		if (tourDates.size() != 0) {
			for (TourDateDTO tourDateDTO : tourDates) {
				tourDateMap.put((tourDateDTO.getDateStartString() + " – " + tourDateDTO.getDateEndString()),
						tourDateDTO.getId());
			}
		}

		return new ResponseEntity<Map<String, Integer>>(tourDateMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/personcount", method = RequestMethod.GET)
	public ResponseEntity<Map<Integer, Integer>> getPersonCount(
			@RequestParam(name = "tourDateId", required = true) final Integer tourDateId) {

		ITourDate tourDate = tourDateService.getById(tourDateId);
		Map<Integer, Integer> personCount = new HashMap<>();

		for (int i = 1; i <= tourDate.getNumPerson(); i++) {
			personCount.put(i, i);
		}

		return new ResponseEntity<Map<Integer, Integer>>(personCount, HttpStatus.OK);
	}

}
