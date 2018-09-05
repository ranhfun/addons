package com.ranhfun.oauth2.services;

import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.tynamo.security.internal.services.LoginContextService;

import com.ranhfun.oauth2.authc.OAuthFilter;

@EagerLoad
public class OAuthSecurityFilterChainFactoryImpl implements
		OAuthSecurityFilterChainFactory {

	private final LoginContextService loginContextService;
	
	public OAuthSecurityFilterChainFactoryImpl(LoginContextService loginContextService) {
		this.loginContextService = loginContextService;
	}
	
	public OAuthFilter oauth(String failUrl) {
		OAuthFilter oAuthFilter = new OAuthFilter(loginContextService);
		oAuthFilter.setFailureUrl(failUrl);
		return oAuthFilter;
	}

}
