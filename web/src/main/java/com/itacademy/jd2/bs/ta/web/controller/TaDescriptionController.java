package com.itacademy.jd2.bs.ta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.service.ITaDescriptionService;
import com.itacademy.jd2.bs.ta.web.converter.fromDTO.TaDescriptionFromDTOConverter;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.TaDescriptionToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.TaDescriptionDTO;

@Controller
@RequestMapping(value = "/description")
public class TaDescriptionController extends AbstractController<TaDescriptionDTO> {

	private ITaDescriptionService taDescriptionService;

	private TaDescriptionToDTOConverter toDtoConverter;
	private TaDescriptionFromDTOConverter fromDtoConverter;

	@Autowired
	private TaDescriptionController(final ITaDescriptionService taDescriptionService,
			final TaDescriptionToDTOConverter toDtoConverter, final TaDescriptionFromDTOConverter fromDtoConverter) {
		super();
		this.taDescriptionService = taDescriptionService;
		this.toDtoConverter = toDtoConverter;
		this.fromDtoConverter = fromDtoConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(final HttpServletRequest req) {

		final List<ITaDescription> entities = taDescriptionService.getAll();
		List<TaDescriptionDTO> dtos = entities.stream().map(toDtoConverter).collect(Collectors.toList());

		final Map<String, Object> models = new HashMap<>();
		models.put("entity", dtos);

		return new ModelAndView("tadescription.list", models);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("formModel") final TaDescriptionDTO formModel,
			final BindingResult result) {
		if (result.hasErrors()) {
			return "tadescription.edit";
		} else {
			final ITaDescription entity = fromDtoConverter.apply(formModel);
			taDescriptionService.save(entity);
			return "redirect:/description";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView showForm() {
		final Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("formModel", new TaDescriptionDTO());
		return new ModelAndView("tadescription.edit", hashMap);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(name = "id", required = true) final Integer id) {
		taDescriptionService.delete(id);
		return "redirect:/description";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
		final TaDescriptionDTO dto = toDtoConverter.apply(taDescriptionService.getById(id));
		final Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("formModel", dto);
		return new ModelAndView("tadescription.edit", hashMap);
	}

}