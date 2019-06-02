package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.web.dto.CustomerDTO;

@Component
public class CustomerToDTOConverter implements Function<ICustomer, CustomerDTO> {

	@Override
	public CustomerDTO apply(final ICustomer entity) {
		final CustomerDTO dto = new CustomerDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setStatus(entity.getStatus());
		dto.setMiddleName(entity.getMiddleName());
		dto.setBirthday(entity.getBirthday());
		dto.setPassportNumber(entity.getPassportNumber());
		dto.setPassportStart(entity.getPassportStart());
		dto.setPassportEnd(entity.getPassportEnd());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setCountry(entity.getCountry());
		dto.setCity(entity.getCity());
		dto.setStreet(entity.getStreet());
		dto.setBonus(entity.getBonus());

		dto.setEmail(entity.getUserAccount().getEmail());
		dto.setFirstName(entity.getUserAccount().getFirstName());
		dto.setLastName(entity.getUserAccount().getLastName());
		dto.setRole(entity.getUserAccount().getRole());

		return dto;
	}

}
