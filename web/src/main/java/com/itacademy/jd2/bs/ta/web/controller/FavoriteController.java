package com.itacademy.jd2.bs.ta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;
import com.itacademy.jd2.bs.ta.service.IFavoriteService;
import com.itacademy.jd2.bs.ta.web.converter.fromDTO.FavoriteFromDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.FavoriteDTO;

@Controller
@RequestMapping(value = "/favorite")
public class FavoriteController extends AbstractController<FavoriteDTO> {

	private IFavoriteService favoriteService;

	private FavoriteFromDTOConverter fromDtoConverter;

	@Autowired
	public FavoriteController(final IFavoriteService favoriteService, final FavoriteFromDTOConverter fromDtoConverter) {
		super();
		this.favoriteService = favoriteService;
		this.fromDtoConverter = fromDtoConverter;
	}

	@RequestMapping(value = "/adddelete", method = RequestMethod.GET)
	public ResponseEntity<String> add(@RequestParam(name = "tourId", required = true) final Integer tourId) {
		boolean exist = false;
		Integer favoriteId = null;

		final Map<String, Long> map = new HashMap<>();
		String result = null;
		Gson gson = new Gson();

		final FavoriteFilter filter = new FavoriteFilter();
		final List<IFavorite> entities = favoriteService.getByCustomerId(getLoggedUserId(), filter);

		for (IFavorite favorite : entities) {
			if (favorite.getTour().getId() == tourId) {
				exist = true;
				favoriteId = favorite.getId();
				break;
			} else {
				exist = false;
			}
		}

		if (!exist) {
			FavoriteDTO dto = new FavoriteDTO();
			dto.setCustomerId(getLoggedUserId());
			dto.setTourId(tourId);
			final IFavorite entity = fromDtoConverter.apply(dto);
			favoriteService.save(entity);
			long favoriteCount = favoriteService.getCountByTourId(tourId);
			map.put("result", (long) 1);
			map.put("count", favoriteCount);
			result = gson.toJson(map);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			try {
				favoriteService.delete(favoriteId);
				long favoriteCount = favoriteService.getCountByTourId(tourId);
				map.put("result", (long) 0);
				map.put("count", favoriteCount);
				result = gson.toJson(map);
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		}

	}

}
