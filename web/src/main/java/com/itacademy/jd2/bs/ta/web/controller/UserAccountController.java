package com.itacademy.jd2.bs.ta.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;
import com.itacademy.jd2.bs.ta.web.dto.UserAccountDTO;

@Controller
@RequestMapping(value = "/user")
public class UserAccountController extends AbstractController<UserAccountDTO> {

	private IUserAccountService userAccountService;

	@Autowired
	public UserAccountController(final IUserAccountService userAccountService) {
		super();
		this.userAccountService = userAccountService;
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ResponseEntity<String> changePassword(@RequestParam(name = "id", required = true) final Integer userId,
			@RequestParam(name = "oldPassword", required = false) final String receivedOldPassword,
			@RequestParam(name = "newPassword", required = false) final String receivedNewPassword) {

		boolean check = false;
		String result = null;

		if (getLoggedUserId() != null && getLoggedUserId() == userId) {

			IUserAccount user = userAccountService.getById(getLoggedUserId());
			final String password = DigestUtils.md5Hex(receivedOldPassword).toUpperCase();

			if (user.getPassword().equals(password)) {
				check = true;
			} else {
				result = "Wrong password.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}

			if (receivedNewPassword.length() >= 6 && check) {
				String newPassword = DigestUtils.md5Hex(receivedNewPassword).toUpperCase();

				if (password.equals(newPassword)) {
					result = "The new password cannot be the same as the old one.";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				} else {
					user.setPassword(newPassword);
					try {
						userAccountService.save(user);
						result = "Done!";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					} catch (NotUniqueUserException e) {
						result = "Unexpected error.";
						return new ResponseEntity<String>(result, HttpStatus.OK);
					}
				}

			} else {
				result = "New password must be longer than 6 characters.";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}

		} else {
			result = "You can't manage this account.";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Boolean> updateStatus(@RequestParam(name = "date", required = true) final String date) {
		if (getLoggedUserId() != null) {
			IUserAccount user = userAccountService.getById(getLoggedUserId());
			user.setUpdated(new Date(Long.valueOf(date)));
			try {
				userAccountService.save(user);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (NotUniqueUserException e) {
				e.printStackTrace();
				return new ResponseEntity<Boolean>(false, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/onlinestatus", method = RequestMethod.POST)
	public ResponseEntity<Boolean> getStatus(@RequestParam(name = "id", required = true) final Integer id) {
		IUserAccount user = userAccountService.getById(id);

		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		Calendar userUpdated = Calendar.getInstance();
		userUpdated.setTime(user.getUpdated());

		long diff = TimeUnit.MILLISECONDS
				.toMinutes(Math.abs(currentDate.getTimeInMillis() - userUpdated.getTimeInMillis()));
		if (diff <= 1) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

	}

}
