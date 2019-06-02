package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.web.dto.CountryDTO;

@Component
public class CountryFromDTOConverter implements Function<CountryDTO, ICountry> {

	private ICountryService countryService;

	@Autowired
	public CountryFromDTOConverter(final ICountryService countryService) {
		super();
		this.countryService = countryService;
	}

	@Override
	public ICountry apply(final CountryDTO dto) {
		final ICountry entity = countryService.createEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}

}
