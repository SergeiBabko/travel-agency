package com.itacademy.jd2.bs.ta.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthHelper {

	private AuthHelper() {
	}

	public static Integer getLoggedUserId() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (!(authentication instanceof ExtendedUsernamePasswordAuthenticationToken)) {
			return null;
		}
		ExtendedUsernamePasswordAuthenticationToken extAuthentication = (ExtendedUsernamePasswordAuthenticationToken) authentication;
		return extAuthentication.getId();
	}

	public static String getLoggedUserRole() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (!(authentication instanceof ExtendedUsernamePasswordAuthenticationToken)) {
			return null;
		}
		ExtendedUsernamePasswordAuthenticationToken extAuthentication = (ExtendedUsernamePasswordAuthenticationToken) authentication;
		return extAuthentication.getRole();
	}

}
