package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.web.dto.CustomerDTO;

@Component
public class CustomerFromDTOConverter implements Function<CustomerDTO, ICustomer> {

	private ICustomerService customerService;

	@Autowired
	public CustomerFromDTOConverter(final ICustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@Override
	public ICustomer apply(final CustomerDTO dto) {
		final ICustomer entity = customerService.createEntity();
		entity.setId(dto.getId());
		entity.setStatus(dto.getStatus());
		entity.setMiddleName(dto.getMiddleName());
		entity.setBirthday(dto.getBirthday());
		entity.setPassportNumber(dto.getPassportNumber());
		entity.setPassportStart(dto.getPassportStart());
		entity.setPassportEnd(dto.getPassportEnd());
		entity.setPhoneNumber(dto.getPhoneNumber());
		entity.setCountry(dto.getCountry());
		entity.setCity(dto.getCity());
		entity.setStreet(dto.getStreet());
		return entity;
	}

}
