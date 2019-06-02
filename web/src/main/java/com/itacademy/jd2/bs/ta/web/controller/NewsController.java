package com.itacademy.jd2.bs.ta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.NewsFilter;
import com.itacademy.jd2.bs.ta.service.INewsService;
import com.itacademy.jd2.bs.ta.web.converter.fromDTO.NewsFromDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.NewsToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.NewsDTO;
import com.itacademy.jd2.bs.ta.web.dto.list.GridStateDTO;

@Controller
@RequestMapping(value = "/news")
public class NewsController extends AbstractController<NewsDTO> {

	private INewsService newsService;

	private NewsToDTOConverter toDtoConverter;
	private NewsFromDTOConverter fromDtoConverter;

	@Autowired
	private NewsController(final INewsService newsService, final NewsToDTOConverter toDtoConverter,
			final NewsFromDTOConverter fromDtoConverter) {
		super();
		this.newsService = newsService;
		this.toDtoConverter = toDtoConverter;
		this.fromDtoConverter = fromDtoConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(final HttpServletRequest req,
			@RequestParam(name = "page", required = false) final Integer pageNumber,
			@RequestParam(name = "sort", required = false) final String sortColumn) {

		final GridStateDTO gridState = getListDTO(req, 12);
		gridState.setPage(pageNumber);
		gridState.setSort(sortColumn, "id");

		final NewsFilter filter = new NewsFilter();
		prepareFilter(gridState, filter);

		final List<INews> entities = newsService.find(filter);
		List<NewsDTO> dtos = entities.stream().map(toDtoConverter).collect(Collectors.toList());
		gridState.setTotalCount(newsService.getCount());

		final Map<String, Object> models = new HashMap<>();
		models.put("gridItems", dtos);
		return new ModelAndView("news.list", models);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("formModel") final NewsDTO formModel, final BindingResult result) {
		if (result.hasErrors()) {
			return "news.edit";
		} else {
			final INews entity = fromDtoConverter.apply(formModel);
			newsService.save(entity);
			return "redirect:/news";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView showForm() {
		final Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("formModel", new NewsDTO());
		return new ModelAndView("news.edit", hashMap);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
		final NewsDTO dto = toDtoConverter.apply(newsService.getById(id));
		final Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("formModel", dto);
		return new ModelAndView("news.edit", hashMap);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResponseEntity<String> delete(@RequestParam(name = "newsId", required = true) final Integer newsId) {

		String result = null;
		boolean exist = false;

		final String userRole = getLoggedUserRole();
		if (getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.admin.name())
				|| getLoggedUserId() != null && userRole.equalsIgnoreCase(UserRole.moderator.name())) {

			final INews entity = newsService.getById(newsId);
			if (entity != null) {
				exist = true;
			}

			if (exist) {
				newsService.delete(newsId);
				result = "Done!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				result = "This news doesn't exist!";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "You don't have any permissions to edit this content!";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

}
