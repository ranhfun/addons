package com.ranhfun.resteasy.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.slf4j.Logger;

public class ResteasyUT8Filter implements HttpServletRequestFilter {

	private final Logger logger;

	public ResteasyUT8Filter(Logger logger) {
		this.logger = logger;
	}

	public boolean service(final HttpServletRequest request,
			final HttpServletResponse response,
			final HttpServletRequestHandler handler) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		return handler.service(request, response);
	}
}
