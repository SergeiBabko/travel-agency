package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.service.ITourTypeService;
import com.itacademy.jd2.bs.ta.web.dto.TourTypeDTO;

@Component
public class TourTypeFromDTOConverter implements Function<TourTypeDTO, ITourType> {

	private ITourTypeService tourTypeService;

	@Autowired
	public TourTypeFromDTOConverter(final ITourTypeService tourTypeService) {
		super();
		this.tourTypeService = tourTypeService;
	}

	@Override
	public ITourType apply(final TourTypeDTO dto) {
		final ITourType entity = tourTypeService.createEntity();
		entity.setId(dto.getId());
		entity.setType(dto.getType());
		return entity;
	}

}
