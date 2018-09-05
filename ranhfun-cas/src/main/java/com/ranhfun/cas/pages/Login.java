package com.ranhfun.cas.pages;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.internal.services.LoginContextService;

public class Login {

	@Inject
	private LoginContextService loginContextService;
	
	@Inject
	private HttpServletResponse httpServletResponse;

	Object onActivate(String url) throws IOException {
		return loginContextService.getUnauthorizedPage();
	}
}
