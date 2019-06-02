package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.service.ICityService;
import com.itacademy.jd2.bs.ta.service.ICountryService;
import com.itacademy.jd2.bs.ta.web.dto.CityDTO;

@Component
public class CityFromDTOConverter implements Function<CityDTO, ICity> {

	private ICityService cityService;
	private ICountryService countryService;

	@Autowired
	public CityFromDTOConverter(final ICityService cityService, final ICountryService countryService) {
		super();
		this.cityService = cityService;
		this.countryService = countryService;
	}

	@Override
	public ICity apply(final CityDTO dto) {
		final ICity entity = cityService.createEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());

		final ICountry country = countryService.createEntity();
		country.setId(dto.getCountryId());
		country.setName(dto.getCountryName());
		entity.setCountry(country);

		return entity;
	}

}
