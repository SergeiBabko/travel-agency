package com.itacademy.jd2.bs.ta.web.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ExtendedUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String firstName;
	private String lastName;
	private String role;

	public ExtendedUsernamePasswordAuthenticationToken(final Integer id, final Object principal,
			final Object credentials, final Collection<? extends GrantedAuthority> authorities, final String firstName,
			final String lastName, final String role) {
		super(principal, credentials, authorities);

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRole() {
		return role;
	}

}
