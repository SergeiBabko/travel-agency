package com.itacademy.jd2.bs.ta.service.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.service.IMailService;

@Service
public class EmailSender implements IMailService {

	@Autowired
	private SmtpProperties smtpProp;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

	private Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpProp.getHost());
		props.put("mail.smtp.socketFactory.port", smtpProp.getSocketFactoryPort());
		props.put("mail.smtp.socketFactory.class", smtpProp.getSocketFactoryClass());
		props.put("mail.smtp.auth", smtpProp.getAuth());
		props.put("mail.smtp.port", smtpProp.getPort());

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpProp.getUser(), smtpProp.getPassword());
			}
		});
		return session;
	}

	public void sendEmail(final IUserAccount user, final String headline) {

		Session session = getSession();

		try {

			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(smtpProp.getUser(), smtpProp.getFrom()));
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("Unsupported encoding of sender name.");
			}
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject(headline);

			message.setContent(getHTML("..\\service\\src\\main\\resources\\registration-message.html"),
					"text/html; charset=utf-8");

			Transport.send(message);

			LOGGER.info("Email \"{}\" has been sent  to user: {}", headline, user);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendEmail(final IUserAccount user, final String headline, final String newPassword) {

		Session session = getSession();

		try {

			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(smtpProp.getUser(), smtpProp.getFrom()));
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("Unsupported encoding of sender name.");
			}
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject(headline);

			String html = (String) getHTML("..\\service\\src\\main\\resources\\pass-or-account-restore.html");
			html = html.replace("$USER_EMAIL", user.getEmail()).replace("$USER_PASSWORD", newPassword);

			message.setContent(html, "text/html; charset=utf-8");

			Transport.send(message);

			LOGGER.info("Email \"{}\" has been sent  to user: {}", headline, user);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private Object getHTML(final String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (NoSuchFileException e) {
			LOGGER.info("No such file in diectory: {}", filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}
}
