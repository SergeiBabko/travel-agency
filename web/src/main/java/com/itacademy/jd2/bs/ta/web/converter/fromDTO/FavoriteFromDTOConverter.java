package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.web.dto.FavoriteDTO;

@Component
public class FavoriteFromDTOConverter implements Function<FavoriteDTO, IFavorite> {

	private IFavoriteService favoriteService;
	private ICustomerService customerService;
	private ITourService tourService;

	@Autowired
	public FavoriteFromDTOConverter(final IFavoriteService favoriteService, final  ICustomerService customerService,
			final ITourService tourService) {
		super();
		this.favoriteService = favoriteService;
		this.customerService = customerService;
		this.tourService = tourService;
	}

	@Override
	public IFavorite apply(final FavoriteDTO dto) {
		final IFavorite entity = favoriteService.createEntity();
		entity.setId(dto.getId());

		final ICustomer customer = customerService.createEntity();
		customer.setId(dto.getCustomerId());
		entity.setCustomer(customer);

		final ITour tour = tourService.getById(dto.getTourId());
		entity.setTour(tour);

		return entity;
	}

}
