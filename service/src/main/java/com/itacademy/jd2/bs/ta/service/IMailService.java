package com.itacademy.jd2.bs.ta.service;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;

public interface IMailService {

	void sendEmail(IUserAccount user, String headline);

	void sendEmail(IUserAccount user, String headline, String newPassword);

}