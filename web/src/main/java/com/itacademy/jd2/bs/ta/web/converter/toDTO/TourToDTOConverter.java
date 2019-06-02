package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.web.dto.TourDTO;

@Component
public class TourToDTOConverter implements Function<ITour, TourDTO> {

	@Override
	public TourDTO apply(final ITour entity) {
		final TourDTO dto = new TourDTO();
		dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setTourStatus(entity.getTourStatus());
		dto.setName(entity.getName());
		dto.setPrice(entity.getPrice());

		final ITourType tourType = entity.getTourType();
		final ICity city = entity.getCity();
		final ICountry country = city.getCountry();

		dto.setTourTypeId(tourType.getId());
		dto.setNights(entity.getNights());
		dto.setImage(entity.getImage());
		dto.setDescription(entity.getDescription());
		dto.setCityId(city.getId());
		dto.setCityName(city.getName());
		dto.setAddress(entity.getAddress());
		dto.setTourTypeName(tourType.getType());
		dto.setCountryId(country.getId());
		dto.setCountryName(country.getName());

		return dto;
	}

}
