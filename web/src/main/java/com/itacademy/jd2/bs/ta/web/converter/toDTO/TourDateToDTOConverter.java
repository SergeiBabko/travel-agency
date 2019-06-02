package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.web.dto.TourDateDTO;

@Component
public class TourDateToDTOConverter implements Function<ITourDate, TourDateDTO> {

	@Override
	public TourDateDTO apply(final ITourDate entity) {
		final TourDateDTO dto = new TourDateDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		final ITour tour = entity.getTour();

		dto.setTourId(tour.getId());
		dto.setNumPerson(entity.getNumPerson());
		dto.setDateStart(entity.getDateStart());
		dto.setDateEnd(entity.getDateEnd());

		DateFormat df = new SimpleDateFormat("dd.MM.yy");

		dto.setDateStartString(df.format(entity.getDateStart()));
		dto.setDateEndString(df.format(entity.getDateEnd()));

		return dto;
	}

}
