package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.web.dto.FavoriteDTO;

@Component
public class FavoriteToDTOConverter implements Function<IFavorite, FavoriteDTO> {

	@Override
	public FavoriteDTO apply(final IFavorite entity) {
		final FavoriteDTO dto = new FavoriteDTO();
		dto.setId(entity.getId());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());

		final ICustomer customer = entity.getCustomer();
		final ITour tour = entity.getTour();

		dto.setCustomerId(customer.getId());
		dto.setTourId(tour.getId());

		dto.setTourName(tour.getName());
		dto.setTourImage(tour.getImage());
		dto.setTourNights(tour.getNights());
		dto.setTourPrice(tour.getPrice());

		return dto;
	}

}
