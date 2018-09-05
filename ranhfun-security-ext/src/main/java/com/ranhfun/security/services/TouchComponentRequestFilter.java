package com.ranhfun.security.services;

import java.io.IOException;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.slf4j.Logger;
import org.tynamo.security.services.SecurityService;

public class TouchComponentRequestFilter implements ComponentRequestFilter {

	private final SecurityService securityService;
	private final Logger logger;
	
	public TouchComponentRequestFilter(SecurityService securityService, Logger logger) {
		this.securityService = securityService;
		this.logger = logger;
	}

	@Override
	public void handleComponentEvent(
			ComponentEventRequestParameters parameters,
			ComponentRequestHandler handler) throws IOException {
		checkInternal();
		handler.handleComponentEvent(parameters);
	}

	@Override
	public void handlePageRender(PageRenderRequestParameters parameters,
			ComponentRequestHandler handler) throws IOException {
		checkInternal();
		handler.handlePageRender(parameters);	
	}

	private void checkInternal() {
		Subject subject = securityService.getSubject();
		if (subject != null) {
            Session session = subject.getSession(false);
            if (session != null) {
                try {
                    session.touch();
                } catch (Throwable t) {
                	logger.error("session.touch() method invocation has failed.  Unable to update" +
                            "the corresponding session's last access time based on the incoming request.", t);
                }
            }
        }
	}
}
