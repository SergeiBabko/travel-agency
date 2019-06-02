package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.service.IBookedService;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.web.dto.BookedDTO;

@Component
public class BookedFromDTOConverter implements Function<BookedDTO, IBooked> {

	private IBookedService bookedService;
	private ICustomerService customerService;
	private ITourDateService tourDateService;

	@Autowired
	public BookedFromDTOConverter(final IBookedService bookedService, final ICustomerService customerService,
			final ITourDateService tourDateService) {
		super();
		this.bookedService = bookedService;
		this.customerService = customerService;
		this.tourDateService = tourDateService;
	}

	@Override
	public IBooked apply(final BookedDTO dto) {
		final IBooked entity = bookedService.createEntity();
		entity.setId(dto.getId());

		final ICustomer customer = customerService.createEntity();
		customer.setId(dto.getCustomerId());
		entity.setCustomer(customer);

		final ITourDate tourDate = tourDateService.createEntity();
		tourDate.setId(dto.getTourDateId());
		entity.setTourDate(tourDate);

		entity.setNumPerson(dto.getNumPerson());
		entity.setPrice(dto.getPrice());
		entity.setMessage(dto.getMessage());
		entity.setProcessed(dto.getProcessed());
		return entity;
	}

}
