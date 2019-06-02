package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IReviewService;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.web.dto.ReviewDTO;

@Component
public class ReviewFromDTOConverter implements Function<ReviewDTO, IReview> {

	private IReviewService reviewService;
	private ICustomerService customerService;
	private ITourDateService tourDateService;

	@Autowired
	public ReviewFromDTOConverter(final IReviewService reviewService, final  ICustomerService customerService,
			final ITourDateService tourDateService) {
		super();
		this.reviewService = reviewService;
		this.customerService = customerService;
		this.tourDateService = tourDateService;
	}

	@Override
	public IReview apply(final ReviewDTO dto) {
		final IReview entity = reviewService.createEntity();
		entity.setId(dto.getId());
		entity.setReview(dto.getReview());
		entity.setRating(dto.getRating());

		final ICustomer customer = customerService.createEntity();
		customer.setId(dto.getCustomerId());
		entity.setCustomer(customer);

		final ITourDate tourDate = tourDateService.createEntity();
		tourDate.setId(dto.getTourDateId());
		entity.setTourDate(tourDate);

		return entity;
	}

}
