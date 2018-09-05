package com.ranhfun.cas.services;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.shiro.authc.CasFilter;

import com.ranhfun.cas.CasSymbols;

public class CasSecurityFilterChainFactoryImpl implements
		CasSecurityFilterChainFactory {

	private final LoginContextService loginContextService;
	private final String casFailureUrl;

	public CasSecurityFilterChainFactoryImpl(
			LoginContextService loginContextService,
			@Inject @Symbol(CasSymbols.CAS_FAILURE_URL) String casFailureUrl) {
		this.loginContextService = loginContextService;
		this.casFailureUrl = casFailureUrl;
	}

	public CasFilter cas() {
		String name = "cas";
		CasFilter filter = new CasFilter(loginContextService) {
			private String fallbackUrl;

			protected boolean onAccessDenied(ServletRequest request,
					ServletResponse response) throws Exception {
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				fallbackUrl = WebUtils.getPathWithinApplication(httpServletRequest);
				if (httpServletRequest.getQueryString() != null)
					fallbackUrl += "?" + httpServletRequest.getQueryString();
				return executeLogin(request, response);
			}

			protected boolean isAccessAllowed(ServletRequest request,
					ServletResponse response, Object mappedValue) {
				Subject subject = getSubject(request, response);
				return subject.isAuthenticated();
			}

			protected boolean onLoginFailure(AuthenticationToken token,
					AuthenticationException ae, ServletRequest request,
					ServletResponse response) {
				Subject subject = getSubject(request, response);
				if (!subject.isAuthenticated() && !subject.isRemembered()) {
					setRedirectToSavedUrl(true);
	            	saveRequest(request);
				}
				return super.onLoginFailure(token, ae, request, response);
			}

			protected void issueSuccessRedirect(ServletRequest request,
					ServletResponse response) throws Exception {
				if (fallbackUrl != null) {
					getLoginContextService().redirectToSavedRequest(fallbackUrl);
				} else {
					super.issueSuccessRedirect(request, response);
				}
			}
			
			public boolean isRedirectToSavedUrl() {
				return true;
			}
		};
		filter.setName(name);
		filter.setFailureUrl(casFailureUrl);
		return filter;
	}

	public String getCasFailureUrl() {
		return casFailureUrl;
	}
}
