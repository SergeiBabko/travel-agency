package com.itacademy.jd2.bs.ta.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.itacademy.jd2.bs.ta.web.security.ExtendedUsernamePasswordAuthenticationToken;

public class UserNameLable extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		final JspContext jspContext = getJspContext();

		SecurityContext context = SecurityContextHolder.getContext();
		ExtendedUsernamePasswordAuthenticationToken authentication = (ExtendedUsernamePasswordAuthenticationToken) context
				.getAuthentication();

		StringBuilder sb = new StringBuilder();

		if (authentication.getFirstName() != null && authentication.getLastName() != null) {
			sb.append(authentication.getFirstName());
			sb.append(" ");
			sb.append(authentication.getLastName());
		} else if (authentication.getFirstName() != null && authentication.getLastName() == null) {
			sb.append(authentication.getFirstName());
		} else if (authentication.getFirstName() == null && authentication.getLastName() != null) {
			sb.append(authentication.getLastName());
		}

		jspContext.getOut().println(sb);
	}

}
