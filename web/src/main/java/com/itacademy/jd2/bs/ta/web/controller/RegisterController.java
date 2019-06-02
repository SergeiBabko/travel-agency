package com.itacademy.jd2.bs.ta.web.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IMailService;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	private ICustomerService customerService;
	private IUserAccountService userAccountService;
	private IMailService emailSender;

	@Autowired
	public RegisterController(final ICustomerService customerService, final IUserAccountService userAccountService,
			final IMailService emailSender) {
		super();
		this.customerService = customerService;
		this.userAccountService = userAccountService;
		this.emailSender = emailSender;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestParam(name = "email", required = true) final String email,
			@RequestParam(name = "password", required = true) final String password) {

		String result = null;

		if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password)) {
			if (userAccountService.validateEmail(email)) {
				if (password.replaceAll(" ", "").length() >= 6) {

					IUserAccount user = userAccountService.createEntity();
					user.setEmail(email);
					user.setPassword(DigestUtils.md5Hex(password).toUpperCase());

					ICustomer customer = customerService.createEntity();

					try {
						customerService.save(user, customer);
						emailSender.sendEmail(user, "Welcome to Travel Agency!");
						result = "Done!";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					} catch (NotUniqueUserException e) {
						result = "User with this email is already exists.";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					} catch (Exception e) {
						result = "User with this email is already exists.";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					}

				} else {
					result = "Password must be longer than 6 characters.";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			} else {
				result = "You entered the wrong email address. Try again.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} else {
			result = "You need to enter real email and password to register in this app.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}
}
