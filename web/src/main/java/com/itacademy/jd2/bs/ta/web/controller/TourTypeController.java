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

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;
import com.itacademy.jd2.bs.ta.web.dto.TourTypeDTO;

@Controller
@RequestMapping(value = "/type")
public class TourTypeController extends AbstractController<TourTypeDTO> {

	private ITourTypeService tourTypeService;

	@Autowired
	public TourTypeController(final ITourTypeService tourTypeService) {
		super();
		this.tourTypeService = tourTypeService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> add(
			@RequestParam(name = "name", required = true) final String tourTypeName) {

		final Map<String, Object> response = new HashMap<>();

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (!StringUtils.isBlank(tourTypeName)) {

				final ITourType entity = tourTypeService.createEntity();
				entity.setType(tourTypeName);
				try {
					tourTypeService.save(entity);
					long count = tourTypeService.getCount();
					response.put("result", true);
					response.put("count", count);
					response.put("message", "Done!");
					response.put("tourTypeId", entity.getId());
					response.put("tourTypeName", entity.getType());
					DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
					String date = df.format(entity.getCreated());
					response.put("tourTypeCreated", date);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.put("result", false);
					response.put("message", "This tour type is already exist!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}

			} else {
				response.put("result", false);
				response.put("message", "To create new tour type you must enter correct tour type name.");
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
				tourTypeService.delete(id);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (Exception e) {
				result = "You can't remove the tour type, which is referred to the tour.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "Tour type with this id doesn't exists.";
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
				final ITourType entity = tourTypeService.getById(id);
				entity.setType(name);
				try {
					tourTypeService.save(entity);
					response.put("result", true);
					response.put("tourTypeName", entity.getType());
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.put("result", false);
					response.put("message", "This tour type is already exist!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
			} else {
				response.put("result", false);
				response.put("message", "To rename tour type you must enter correct tour type name.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}

		} else {
			response.put("result", false);
			response.put("message", "You don't have any permissions to add this content!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

}
