package com.itacademy.jd2.bs.ta.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.ICustomerService;
import com.itacademy.jd2.bs.ta.service.IMailService;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;

@Controller
@RequestMapping(value = "/recovercustomer")
public class RecoverCustomerAccountController {

	private ICustomerService customerService;
	private IUserAccountService userAccountService;
	private IMailService emailSender;

	@Autowired
	public RecoverCustomerAccountController(final ICustomerService customerService,
			final IUserAccountService userAccountService, final IMailService emailSender) {
		super();
		this.customerService = customerService;
		this.userAccountService = userAccountService;
		this.emailSender = emailSender;
	}

	@RequestMapping(value = "/restore", method = RequestMethod.GET)
	public ResponseEntity<String> tourDateAdd(@RequestParam(name = "email", required = true) final String email) {
		boolean a = userAccountService.validateEmail(email);
		boolean b;
		final Map<String, String> map = new HashMap<>();
		String result;
		Gson gson = new Gson();

		if (!a) {
			map.put("result", "Wrong email address!");
			map.put("message", "You entered the wrong email address. Try again.");
			result = gson.toJson(map);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

		try {
			IUserAccount user = userAccountService.getByEmail(email);
			b = user.getCustomer().getStatus() == CustomerStatus.blocked;
		} catch (IllegalArgumentException e) {
			map.put("result", "User with email: " + email + " doesn't exist.");
			map.put("message",
					"You need to register on our site before performing this operation or enter right email address.");
			result = gson.toJson(map);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

		if (b) {
			map.put("result", "You have been blocked on our site!");
			map.put("message", "You can no longer use your bonuses from this account.");
			result = gson.toJson(map);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			IUserAccount uAccount = userAccountService.getByEmail(email);
			ICustomer customer = customerService.getById(uAccount.getId());

			final int length = 10;
			final boolean useLetters = true;
			final boolean useNumbers = true;
			final String newPassword = RandomStringUtils.random(length, useLetters, useNumbers);
			final String md5Password = DigestUtils.md5Hex(newPassword).toUpperCase();

			uAccount.setPassword(md5Password);
			try {
				userAccountService.save(uAccount);
			} catch (NotUniqueUserException e) {
				map.put("result", "Save error");
				map.put("message", "Not unique user.");
				result = gson.toJson(map);
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}

			IUserAccount entity = userAccountService.getByEmail(email);
			if (entity.getPassword().equals(md5Password)) {
				customer.setStatus(CustomerStatus.active);
				customerService.update(customer);
				ICustomer customerFromDb = customerService.getById(uAccount.getId());
				if (customerFromDb.getStatus().equals(customer.getStatus())) {
					emailSender.sendEmail(entity, "Account restore", newPassword);
				}
			}
			map.put("result", "Success!");
			map.put("message", "Your account has been restored. Check your email for details.");
			result = gson.toJson(map);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	}

}
