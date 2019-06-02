package com.itacademy.jd2.bs.ta.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IReviewService;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.web.dto.ReviewDTO;
import com.itacademy.jd2.bs.ta.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/review")
public class ReviewController extends AbstractController<ReviewDTO> {

	private IReviewService reviewService;
	private ICustomerService customerService;
	private ITourDateService tourDateService;

	@Autowired
	public ReviewController(final IReviewService reviewService, final ICustomerService customerService,
			final ITourDateService tourDateService) {
		super();
		this.reviewService = reviewService;
		this.customerService = customerService;
		this.tourDateService = tourDateService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> add(
			@RequestParam(name = "tourDateId", required = true) final String receivedTourDateId,
			@RequestParam(name = "review", required = true) final String receivedReview,
			@RequestParam(name = "rating", required = true) final String receivedRating) {

		String result = null;

		final String userRole = AuthHelper.getLoggedUserRole();
		if (AuthHelper.getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {

			if (!StringUtils.isBlank(receivedTourDateId) && !StringUtils.isBlank(receivedRating)
					&& !StringUtils.isBlank(receivedReview)) {

				final Integer tourDateId = Integer.valueOf(receivedTourDateId);
				final Integer rating = Integer.valueOf(receivedRating);

				IReview review = reviewService.createEntity();
				ICustomer customer = customerService.getById(AuthHelper.getLoggedUserId());
				ITourDate tourDate = tourDateService.getById(tourDateId);

				review.setTourDate(tourDate);
				review.setCustomer(customer);
				review.setRating(rating);
				review.setReview(receivedReview);

				reviewService.save(review);

				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);

			} else {
				result = "To review a tour date you must fill in all fields correct.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}

		} else {
			result = "You don't have any permissions to edit this content!";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResponseEntity<String> delete(@RequestParam(name = "reviewId", required = true) final Integer reviewId) {

		String result = null;
		boolean exist = false;

		final String userRole = AuthHelper.getLoggedUserRole();
		if (AuthHelper.getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			final ReviewFilter filter = new ReviewFilter();
			final List<IReview> entities = reviewService.getByCustomerId(filter, AuthHelper.getLoggedUserId());

			for (IReview review : entities) {
				if (review.getId() == reviewId) {
					exist = true;
					break;
				} else {
					exist = false;
				}
			}

			if (exist) {
				reviewService.delete(reviewId);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				result = "This isn't your review!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else if (AuthHelper.getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.admin.name())
				|| AuthHelper.getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.moderator.name())) {

			try {
				final IReview entity = reviewService.getById(reviewId);
				if (entity != null) {
					exist = true;
				}
			} catch (Exception e) {
				exist = false;
			}

			if (exist) {
				try {
					reviewService.delete(reviewId);
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

}
