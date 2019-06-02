package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.web.dto.CityDTO;

@Component
public class CityToDTOConverter implements Function<ICity, CityDTO> {

	private ICountryService countryService;

	@Autowired
	public CityToDTOConverter(final ICountryService countryService) {
		super();
		this.countryService = countryService;
	}

	@Override
	public CityDTO apply(final ICity entity) {
		final CityDTO dto = new CityDTO();

		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setName(entity.getName());

		final ICountry country = entity.getCountry();
		dto.setCountryId(country.getId());
		dto.setCountryName(countryService.getById(country.getId()).getName());

		return dto;
	}

}
