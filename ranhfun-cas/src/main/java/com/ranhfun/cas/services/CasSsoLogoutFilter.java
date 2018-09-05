package com.ranhfun.cas.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.jasig.cas.client.session.SingleSignOutHandler;
import org.slf4j.Logger;

public class CasSsoLogoutFilter implements HttpServletRequestFilter {

	private static final SingleSignOutHandler signOutHandler = new SingleSignOutHandler();

	private final Logger logger;

	public CasSsoLogoutFilter(Logger logger) {
		this.logger = logger;
	}

	public boolean service(final HttpServletRequest request,
			final HttpServletResponse response,
			final HttpServletRequestHandler handler) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if (signOutHandler.isTokenRequest(request)) {
			signOutHandler.recordSession(request);
		} else if (signOutHandler.isLogoutRequest(request)) {
			signOutHandler.destroySession(request);
			// Do not continue up filter chain
			return false;
		} else {
			logger.trace("Ignoring URI " + request.getRequestURI());
		}
		return handler.service(request, response);
	}
}
