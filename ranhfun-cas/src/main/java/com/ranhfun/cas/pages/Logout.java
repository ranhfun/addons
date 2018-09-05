package com.ranhfun.cas.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Cookies;
import org.tynamo.security.services.SecurityService;

import com.ranhfun.cas.CasSymbols;

public class Logout {

	@Inject
	private SecurityService securityService;
	
	@Inject @Symbol(CasSymbols.CAS_LOGOUT_URL)
	private String logoutUrl;
	
	@Inject
	private Cookies cookies;
	
	Object onActivate() throws MalformedURLException {
		cookies.removeCookieValue(WebUtils.SAVED_REQUEST_KEY);
		securityService.getSubject().logout();
		return new URL(logoutUrl);
	}
}
