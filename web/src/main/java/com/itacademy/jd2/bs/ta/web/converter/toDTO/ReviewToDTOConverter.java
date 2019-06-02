package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.web.dto.ReviewDTO;

@Component
public class ReviewToDTOConverter implements Function<IReview, ReviewDTO> {

	@Override
	public ReviewDTO apply(final IReview entity) {
		final ReviewDTO dto = new ReviewDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setReview(entity.getReview());
		dto.setRating(entity.getRating());

		final ICustomer customer = entity.getCustomer();
		final ITourDate tourDate = entity.getTourDate();

		dto.setCustomerId(customer.getId());
		dto.setTourDateId(tourDate.getId());

		if (customer.getUserAccount().getFirstName() != null) {
			dto.setUserFirstName(customer.getUserAccount().getFirstName());
		}
		if (customer.getUserAccount().getLastName() != null) {
			dto.setUserLastName(customer.getUserAccount().getLastName());
		}

		dto.setTourDateStart(tourDate.getDateStart());
		dto.setTourDateEnd(tourDate.getDateEnd());

		dto.setTourId(tourDate.getTour().getId());
		dto.setTourName(tourDate.getTour().getName());
		dto.setTourImage(tourDate.getTour().getImage());

		return dto;
	}

}
