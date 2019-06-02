package com.itacademy.jd2.bs.ta.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.NewsFilter;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.INewsService;
import com.itacademy.jd2.bs.ta.service.ITourService;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.NewsToDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TourToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.NewsDTO;
import com.itacademy.jd2.bs.ta.web.dto.TourDTO;
import com.itacademy.jd2.bs.ta.web.security.AuthHelper;
import com.itacademy.jd2.bs.ta.web.tag.I18N;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

	private INewsService newsService;
	private ITourService tourService;
	private ICustomerService customerService;

	private NewsToDTOConverter newsToDtoConverter;
	private TourToDTOConverter tourToDtoConverter;

	private static final Locale LOCALE_RU = new Locale("ru");
	private static final Locale LOCALE_EN = new Locale("en");

	@Autowired
	public DefaultController(final INewsService newsService, final ITourService tourService,
			final ICustomerService customerService, final NewsToDTOConverter newsToDtoConverter,
			final TourToDTOConverter tourToDtoConverter) {
		super();
		this.newsService = newsService;
		this.tourService = tourService;
		this.customerService = customerService;
		this.newsToDtoConverter = newsToDtoConverter;
		this.tourToDtoConverter = tourToDtoConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(final HttpServletRequest req,
			@RequestParam(name = "lang", required = false) final String lang) {

		if (lang != null) {

			Locale locale;
			if ("ru".equals(lang)) {
				locale = LOCALE_RU;
			} else {
				locale = LOCALE_EN;
			}

			req.getSession().setAttribute(I18N.SESSION_LOCALE_KEY, locale);
		}

		String userRole = AuthHelper.getLoggedUserRole();

		final NewsFilter newsFilter = new NewsFilter();
		newsFilter.setSortColumn("id");
		newsFilter.setSortOrder(false);

		final List<INews> news = newsService.find(newsFilter);
		List<NewsDTO> newsDTOs = new ArrayList<>();
		if (news.size() >= 3) {
			for (int i = 0; i < 3; i++) {
				NewsDTO dto = newsToDtoConverter.apply(news.get(i));
				newsDTOs.add(dto);
			}
		} else {
			newsDTOs = news.stream().map(newsToDtoConverter).collect(Collectors.toList());
		}

		final TourFilter tourFilter = new TourFilter();
		tourFilter.setSortColumn("id");
		tourFilter.setSortOrder(false);

		final List<ITour> tours = tourService.find(tourFilter);
		List<TourDTO> toursDTOs = new ArrayList<>();
		if (tours.size() > 5) {
			for (int i = 0; i < 5; i++) {
				TourDTO dto = tourToDtoConverter.apply(tours.get(i));
				toursDTOs.add(dto);
			}
		} else {
			toursDTOs = tours.stream().map(tourToDtoConverter).collect(Collectors.toList());
		}

		final Map<String, Object> models = new HashMap<>();

		models.put("news", newsDTOs);
		models.put("tours", toursDTOs);
		getCustomerBonus(userRole, models);

		return new ModelAndView("index", models);
	}

	private void getCustomerBonus(final String userRole, final Map<String, Object> models) {
		if (AuthHelper.getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.user.name())) {
			final Integer customerBonus = customerService.getById(AuthHelper.getLoggedUserId()).getBonus();
			if (customerBonus != null) {
				models.put("customerBonus", customerBonus);
			} else {
				models.put("customerBonus", 0);
			}
		} else {
			models.put("customerBonus", 0);
		}
	}

}