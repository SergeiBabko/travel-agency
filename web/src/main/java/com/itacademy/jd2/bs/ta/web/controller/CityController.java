package com.itacademy.jd2.bs.ta.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.service.ICityService;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.web.dto.CityDTO;

@Controller
@RequestMapping(value = "/city")
public class CityController extends AbstractController<CityDTO> {

	private ICityService cityService;
	private ICountryService countryService;

	@Autowired
	public CityController(final ICityService cityService, final ICountryService countryService) {
		super();
		this.cityService = cityService;
		this.countryService = countryService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> add(@RequestParam(name = "name", required = true) final String cityName,
			@RequestParam(name = "countryId", required = true) final Integer countryId) {

		final Map<String, Object> response = new HashMap<>();

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (!StringUtils.isBlank(cityName) && countryId != null) {

				final ICity entity = cityService.createEntity();
				final ICountry country = countryService.getById(countryId);
				entity.setName(cityName);
				entity.setCountry(country);
				try {
					cityService.save(entity);
					long count = cityService.getCount();
					response.put("result", true);
					response.put("count", count);
					response.put("message", "Done!");
					response.put("cityId", entity.getId());
					response.put("cityName", entity.getName());
					response.put("countryName", entity.getCountry().getName());
					DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
					String date = df.format(entity.getCreated());
					response.put("cityCreated", date);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.put("result", false);
					response.put("message", "This city is already exist!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}

			} else {
				response.put("result", false);
				response.put("message", "To create new city you must enter correct city name.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}

		} else {
			response.put("result", false);
			response.put("message", "You don't have any permissions to add this content!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> delete(@RequestParam(name = "id", required = true) final Integer id) {

		String result = null;
		if (id != null) {
			try {
				cityService.delete(id);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (Exception e) {
				result = "You can't remove the city, which is referred to the country.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "City with this id doesn't exists.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> update(@RequestParam(name = "id", required = true) final Integer id,
			@RequestParam(name = "name", required = false) final String name,
			@RequestParam(name = "countryId", required = true) final Integer countryId) {

		final Map<String, Object> response = new HashMap<>();

		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (!StringUtils.isBlank(name) && countryId != null) {
				final ICity entity = cityService.getById(id);
				final ICountry country = countryService.getById(countryId);
				entity.setName(name);
				entity.setCountry(country);
				try {
					cityService.save(entity);
					response.put("result", true);
					response.put("cityName", entity.getName());
					response.put("countryName", entity.getCountry().getName());
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.put("result", false);
					response.put("message", "This city is already exist!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
			} else {
				response.put("result", false);
				response.put("message", "To rename city you must enter correct city name.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}

		} else {
			response.put("result", false);
			response.put("message", "You don't have any permissions to add this content!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

}
