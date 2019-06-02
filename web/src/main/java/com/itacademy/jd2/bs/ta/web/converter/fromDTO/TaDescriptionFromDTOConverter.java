package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.service.ITaDescriptionService;
import com.itacademy.jd2.bs.ta.web.dto.TaDescriptionDTO;

@Component
public class TaDescriptionFromDTOConverter implements Function<TaDescriptionDTO, ITaDescription> {

	private ITaDescriptionService taDescriptionService;

	@Autowired
	public TaDescriptionFromDTOConverter(final ITaDescriptionService taDescriptionService) {
		super();
		this.taDescriptionService = taDescriptionService;
	}

	@Override
	public ITaDescription apply(final TaDescriptionDTO dto) {
		final ITaDescription entity = taDescriptionService.createEntity();
		entity.setId(dto.getId());
		entity.setDescription(dto.getDescription());
		entity.setContacts(dto.getContacts());
		entity.setAddress(dto.getAddress());
		entity.setImage1(dto.getImage1());
		entity.setImage2(dto.getImage2());
		entity.setImage3(dto.getImage3());
		return entity;
	}

}
