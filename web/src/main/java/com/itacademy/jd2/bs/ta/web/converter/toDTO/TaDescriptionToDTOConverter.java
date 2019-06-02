package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.web.dto.TaDescriptionDTO;

@Component
public class TaDescriptionToDTOConverter implements Function<ITaDescription, TaDescriptionDTO> {

	@Override
	public TaDescriptionDTO apply(final ITaDescription entity) {
		final TaDescriptionDTO dto = new TaDescriptionDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		dto.setDescription(entity.getDescription());
		dto.setContacts(entity.getContacts());
		dto.setAddress(entity.getAddress());
		dto.setImage1(entity.getImage1());
		dto.setImage2(entity.getImage2());
		dto.setImage3(entity.getImage3());

		return dto;
	}

}
