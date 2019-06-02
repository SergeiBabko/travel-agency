package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.web.dto.BookedDTO;

@Component
public class BookedToDTOConverter implements Function<IBooked, BookedDTO> {

	@Override
	public BookedDTO apply(final IBooked entity) {
		final BookedDTO dto = new BookedDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		final ICustomer customer = entity.getCustomer();
		final ITourDate tourDate = entity.getTourDate();

		dto.setCustomerId(customer.getId());
		dto.setTourDateId(tourDate.getId());
		dto.setNumPerson(entity.getNumPerson());
		dto.setPrice(entity.getPrice());
		dto.setMessage(entity.getMessage());
		dto.setProcessed(entity.getProcessed());
		dto.setTourName(tourDate.getTour().getName());
		dto.setTourId(tourDate.getTour().getId());
		dto.setTourImage(tourDate.getTour().getImage());
		dto.setTourDateStart(tourDate.getDateStart());
		dto.setTourDateEnd(tourDate.getDateEnd());
		dto.setTourNights(tourDate.getTour().getNights());

		return dto;
	}

}
