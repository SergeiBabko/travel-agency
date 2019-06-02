package com.itacademy.jd2.bs.ta.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;
import com.itacademy.jd2.bs.ta.web.converter.toDTO.UserAccountToDTOConverter;
import com.itacademy.jd2.bs.ta.web.dto.UserAccountDTO;

@Controller
public class LoginController {

	private IUserAccountService userAccountService;
	private UserAccountToDTOConverter userAccountToDtoConverter;

	@Autowired
	public LoginController(final IUserAccountService userAccountService,
			final UserAccountToDTOConverter userAccountToDtoConverter) {
		super();
		this.userAccountService = userAccountService;
		this.userAccountToDtoConverter = userAccountToDtoConverter;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) final String error,
			@RequestParam(value = "logout", required = false) final String logout) {

		final ModelAndView model = new ModelAndView();
		model.setViewName("login");

		if (error != null) {
			model.addObject("error", "Invalid email or password!");
		}

		if (logout != null) {
			model.addObject("message", "You have been logged out successfully.");
		}

		final IUserAccount user = userAccountService.createEntity();
		UserAccountDTO dto = userAccountToDtoConverter.apply(user);
		model.addObject("formModel", dto);
		model.addObject("userAccount", dto);

		return model;

	}

}
