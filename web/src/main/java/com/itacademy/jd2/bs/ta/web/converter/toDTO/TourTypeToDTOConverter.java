package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.web.dto.TourTypeDTO;

@Component
public class TourTypeToDTOConverter implements Function<ITourType, TourTypeDTO> {

	@Override
	public TourTypeDTO apply(final ITourType entity) {
		final TourTypeDTO dto = new TourTypeDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setType(entity.getType());

		return dto;
	}

}
