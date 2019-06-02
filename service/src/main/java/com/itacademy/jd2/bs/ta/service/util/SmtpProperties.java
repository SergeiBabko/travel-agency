package com.itacademy.jd2.bs.ta.service.util;

import java.io.Serializable;

public class SmtpProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private String host;
	private String socketFactoryPort;
	private String socketFactoryClass;
	private String auth;
	private String port;
	private String from;
	private String user;
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getSocketFactoryPort() {
		return socketFactoryPort;
	}

	public void setSocketFactoryPort(final String socketFactoryPort) {
		this.socketFactoryPort = socketFactoryPort;
	}

	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}

	public void setSocketFactoryClass(final String socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(final String auth) {
		this.auth = auth;
	}

	public String getPort() {
		return port;
	}

	public void setPort(final String port) {
		this.port = port;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
