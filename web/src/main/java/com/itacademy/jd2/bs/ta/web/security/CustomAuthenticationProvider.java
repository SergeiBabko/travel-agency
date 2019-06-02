package com.itacademy.jd2.bs.ta.web.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.service.IUserAccountService;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	private IUserAccountService userAccountService;

	@Autowired
	public CustomAuthenticationProvider(final IUserAccountService userAccountService) {
		super();
		this.userAccountService = userAccountService;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final String username = ((String) authentication.getPrincipal()).toLowerCase();
		final String password = DigestUtils.md5Hex((String) authentication.getCredentials()).toUpperCase();

		LOGGER.info("Entered:  Username={}, Password={}.", username, password);

		IUserAccount user = userAccountService.createEntity();

		try {
			user = userAccountService.getByEmail(username);
		} catch (IllegalArgumentException e) {
			LOGGER.info("User not found.");
		}
		if (user.getId() != null) {
			LOGGER.info(user.toString());
		}

		if (user.getId() == null || !user.getEmail().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("1000");
		}

		if (user.getId() == null || !user.getPassword().equalsIgnoreCase(password)) {
			throw new BadCredentialsException("1000");
		}

		if (user.getCustomer() != null) {
			if (user.getCustomer().getStatus() == CustomerStatus.blocked
					|| user.getCustomer().getStatus() == CustomerStatus.deleted) {
				throw new BadCredentialsException("1000");
			}
		}

		final Integer userId = user.getId();
		final String userFirstName = user.getFirstName();
		final String userLastName = user.getLastName();
		String role = user.getRole().name();
		role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();

		List<String> userRoles = new ArrayList<>();

		userRoles.add("ROLE_" + user.getRole().name());

		final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (String roleName : userRoles) {
			authorities.add(new SimpleGrantedAuthority(roleName));
		}

		return new ExtendedUsernamePasswordAuthenticationToken(userId, username, password, authorities, userFirstName,
				userLastName, role);

	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
