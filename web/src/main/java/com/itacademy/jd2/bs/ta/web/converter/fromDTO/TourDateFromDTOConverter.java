package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.service.ITourDateService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.web.dto.TourDateDTO;

@Component
public class TourDateFromDTOConverter implements Function<TourDateDTO, ITourDate> {

	private ITourDateService tourDateService;
	private ITourService tourService;

	@Autowired
	public TourDateFromDTOConverter(final ITourDateService tourDateService, final ITourService tourService) {
		super();
		this.tourDateService = tourDateService;
		this.tourService = tourService;
	}

	@Override
	public ITourDate apply(final TourDateDTO dto) {
		final ITourDate entity = tourDateService.createEntity();
		entity.setId(dto.getId());

		final ITour tour = tourService.createEntity();
		tour.setId(dto.getTourId());
		entity.setTour(tour);

		entity.setNumPerson(dto.getNumPerson());
		entity.setDateStart(dto.getDateStart());
		entity.setDateEnd(dto.getDateEnd());
		return entity;
	}

}
