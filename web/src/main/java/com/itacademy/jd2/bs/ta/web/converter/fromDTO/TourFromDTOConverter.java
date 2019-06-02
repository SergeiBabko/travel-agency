package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.service.ICityService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;
import com.itacademy.jd2.bs.ta.web.dto.TourDTO;

@Component
public class TourFromDTOConverter implements Function<TourDTO, ITour> {

	private ITourService tourService;
	private ITourTypeService tourTypeService;
	private ICityService cityService;

	@Autowired
	public TourFromDTOConverter(final ITourService tourService, final ITourTypeService tourTypeService,
			final ICityService cityService) {
		super();
		this.tourService = tourService;
		this.tourTypeService = tourTypeService;
		this.cityService = cityService;
	}

	@Override
	public ITour apply(final TourDTO dto) {
		final ITour entity = tourService.createEntity();
		entity.setId(dto.getId());
		entity.setVersion(dto.getVersion());
		entity.setTourStatus(dto.getTourStatus());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());

		final ITourType tourType = tourTypeService.createEntity();
		tourType.setId(dto.getTourTypeId());
		entity.setTourType(tourType);

		entity.setNights(dto.getNights());
		entity.setImage(dto.getImage());
		entity.setDescription(dto.getDescription());

		final ICity city = cityService.createEntity();
		city.setId(dto.getCityId());
		entity.setCity(city);

		entity.setAddress(dto.getAddress());
		return entity;
	}

}
