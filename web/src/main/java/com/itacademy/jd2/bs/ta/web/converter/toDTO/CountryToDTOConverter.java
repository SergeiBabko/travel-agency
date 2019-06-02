package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.web.dto.CountryDTO;

@Component
public class CountryToDTOConverter implements Function<ICountry, CountryDTO> {

	@Override
	public CountryDTO apply(final ICountry entity) {
		final CountryDTO dto = new CountryDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setName(entity.getName());

		return dto;
	}

}
