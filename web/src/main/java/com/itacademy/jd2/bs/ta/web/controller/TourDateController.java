package com.itacademy.jd2.bs.ta.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.web.dto.TourDateDTO;

@Controller
@RequestMapping(value = "/tourdate")
public class TourDateController extends AbstractController<TourDateDTO> {

	private ITourDateService tourDateService;
	private ITourService tourService;

	@Autowired
	public TourDateController(final ITourDateService tourDateService, final ITourService tourService) {
		super();
		this.tourDateService = tourDateService;
		this.tourService = tourService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> tourDateAdd(
			@RequestParam(name = "startDate", required = true) final String recievedStartDate,
			@RequestParam(name = "endDate", required = true) final String recievedEndDate,
			@RequestParam(name = "numPerson", required = true) final String recievedNumPerson,
			@RequestParam(name = "tourId", required = true) final String recievedTourId) {

		final Map<String, Object> response = new HashMap<>();

		final String userRole = getLoggedUserRole();
		if (userRole.equalsIgnoreCase(UserRole.user.name())) {
			response.put("result", false);
			response.put("message", "You don't have any permissions to edit this content!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		Date startDate = null;
		Date endDate = null;
		Integer numPerson = null;
		Integer tourId = null;

		ITourDate entity = tourDateService.createEntity();

		if (!StringUtils.isBlank(recievedStartDate) && !StringUtils.isBlank(recievedEndDate)) {
			startDate = java.sql.Date
					.valueOf(LocalDate.parse(recievedStartDate, DateTimeFormatter.ofPattern("d-M-yyyy")));
			endDate = java.sql.Date.valueOf(LocalDate.parse(recievedEndDate, DateTimeFormatter.ofPattern("d-M-yyyy")));

			Calendar tourStartDate = Calendar.getInstance();
			tourStartDate.setTime(startDate);
			Calendar tourEndDate = Calendar.getInstance();
			tourEndDate.setTime(endDate);

			long diff = TimeUnit.MILLISECONDS
					.toDays(Math.abs(tourEndDate.getTimeInMillis() - tourStartDate.getTimeInMillis()));

			if (startDate.compareTo(endDate) > 0) {
				response.put("result", false);
				response.put("message", "Start Date is after End Date!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			if (startDate.compareTo(endDate) == 0) {
				response.put("result", false);
				response.put("message", "Start Date and End Date can't be the same!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			if (!StringUtils.isBlank(recievedTourId)) {
				tourId = Integer.valueOf(recievedTourId);
				ITour tour = tourService.getById(tourId);
				if (diff < tour.getNights()) {
					response.put("result", false);
					response.put("message",
							"The difference between the dates should't be less than " + tour.getNights() + " days!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
				if (diff > tour.getNights()) {
					response.put("result", false);
					response.put("message",
							"The difference between the dates should't be greater than " + tour.getNights() + " days!");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
			}
		}

		if (!StringUtils.isBlank(recievedStartDate)) {
			startDate = java.sql.Date
					.valueOf(LocalDate.parse(recievedStartDate, DateTimeFormatter.ofPattern("d-M-yyyy")));
			entity.setDateStart(startDate);
		} else {
			response.put("result", false);
			response.put("message", "Start Date is wrong!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		if (!StringUtils.isBlank(recievedEndDate)) {
			endDate = java.sql.Date.valueOf(LocalDate.parse(recievedEndDate, DateTimeFormatter.ofPattern("d-M-yyyy")));
			entity.setDateEnd(endDate);
		} else {
			response.put("result", false);
			response.put("message", "End Date is wrong!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		if (!StringUtils.isBlank(recievedNumPerson)) {
			try {
				numPerson = Integer.valueOf(recievedNumPerson);
				entity.setNumPerson(numPerson);
			} catch (NumberFormatException e) {
				response.put("result", false);
				response.put("message", "Number of person is wrong!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		} else {
			response.put("result", false);
			response.put("message", "Number of person is wrong!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		if (!StringUtils.isBlank(recievedTourId)) {
			tourId = Integer.valueOf(recievedTourId);
			ITour tour = tourService.getById(tourId);
			entity.setTour(tour);
		} else {
			response.put("result", false);
			response.put("message", "Tour number is wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		try {
			tourDateService.save(entity);
			response.put("result", true);
			response.put("message", "Done!");

			DateFormat df = new SimpleDateFormat("dd.MM.yy");
			String dateS = df.format(entity.getDateStart());
			String dateE = df.format(entity.getDateEnd());
			response.put("dateStart", dateS);
			response.put("dateEnd", dateE);
			response.put("dateId", entity.getId());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("result", false);
			response.put("message", "Save error.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResponseEntity<String> delete(@RequestParam(name = "id", required = true) final Integer id) {

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && !userRole.equalsIgnoreCase(UserRole.user.name())) {
			try {

				Calendar currentTime = Calendar.getInstance();

				Date date = tourDateService.getById(id).getCreated();
				Calendar tourCreated = Calendar.getInstance();
				tourCreated.setTime(date);

				long diff = TimeUnit.MILLISECONDS
						.toHours(Math.abs(currentTime.getTimeInMillis() - tourCreated.getTimeInMillis()));

				if (diff < 1) {
					tourDateService.delete(id);
					return new ResponseEntity<String>("Done!", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Date is older than one hour! You can't delete this date.",
							HttpStatus.OK);
				}

			} catch (Exception e) {
				return new ResponseEntity<String>("Someone have used this date. You cant delete it!", HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("You don't have any permissions to edit this content!", HttpStatus.OK);
		}
	}

}
