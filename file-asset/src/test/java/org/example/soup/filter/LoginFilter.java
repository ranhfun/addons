package org.example.soup.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestFilter;
import org.apache.tapestry5.services.PageRenderRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.RequestGlobals;

public class LoginFilter implements PageRenderRequestFilter {

	final ApplicationStateManager sessionManager;
	final RequestGlobals requestGlobals;
	final LinkSource linkSource;
	final HttpServletResponse httpServletResponse;

	public LoginFilter(final ApplicationStateManager sessionManager,
			final RequestGlobals requestGlobals, final LinkSource linkSource,
			final HttpServletResponse httpServletResponse) {
		this.sessionManager = sessionManager;
		this.requestGlobals = requestGlobals;
		this.linkSource = linkSource;
		this.httpServletResponse = httpServletResponse;
	}

	public void handle(PageRenderRequestParameters parameters,
			PageRenderRequestHandler handler) throws IOException {

		/*
		 * SmsUser loginData = null;
		 * 
		 * if (!parameters.getLogicalPageName().equalsIgnoreCase("Users/Login"))
		 * { loginData = sessionManager.getIfExists(SmsUser.class);
		 * 
		 * if (loginData == null ) { requestGlobals.getResponse().sendRedirect(
		 * linkSource.createPageRenderLink("Users/Login", false)
		 * .toRedirectURI()); } }
		 * 
		 * handler.handle(parameters);
		 */
		if (!parameters.getLogicalPageName().equalsIgnoreCase("Next")) {
		requestGlobals.getHTTPServletResponse().sendRedirect(
				linkSource.createPageRenderLink("Next", false).toRedirectURI());
		return;
		}
		handler.handle(parameters);
	}
}
