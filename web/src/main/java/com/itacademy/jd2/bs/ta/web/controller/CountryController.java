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

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.web.dto.CountryDTO;

@Controller
@RequestMapping(value = "/country")
public class CountryController extends AbstractController<CountryDTO> {

	private ICountryService countryService;

	@Autowired
	public CountryController(final ICountryService countryService) {
		super();
		this.countryService = countryService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> add(
			@RequestParam(name = "name", required = true) final String countryName) {

		final Map<String, Object> response = new HashMap<>();

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (!StringUtils.isBlank(countryName)) {

				final ICountry entity = countryService.createEntity();
				entity.setName(countryName);
				try {
					countryService.save(entity);
					long count = countryService.getCount();
					response.put("result", true);
					response.put("count", count);
					response.put("message", "Done!");
					response.put("countryId", entity.getId());
					response.put("countryName", entity.getName());
					DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
					String date = df.format(entity.getCreated());
					response.put("countryCreated", date);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.put("result", false);
					response.put("message", "This country is already exist!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}

			} else {
				response.put("result", false);
				response.put("message", "To create new country you must enter correct country name.");
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
				countryService.delete(id);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (Exception e) {
				result = "You can't remove the country, which is referred to the city.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "Country with this id doesn't exists.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> update(@RequestParam(name = "id", required = true) final Integer id,
			@RequestParam(name = "name", required = false) final String name) {

		final Map<String, Object> response = new HashMap<>();

		String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (!StringUtils.isBlank(name)) {
				final ICountry entity = countryService.getById(id);
				entity.setName(name);
				try {
					countryService.save(entity);
					response.put("result", true);
					response.put("countryName", entity.getName());
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.put("result", false);
					response.put("message", "This country is already exist!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
			} else {
				response.put("result", false);
				response.put("message", "To rename country you must enter correct country name.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}

		} else {
			response.put("result", false);
			response.put("message", "You don't have any permissions to add this content!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

}
