package com.ranhfun.oauth2.services;

import com.ranhfun.oauth2.authc.OAuthFilter;

public interface OAuthSecurityFilterChainFactory {

	public OAuthFilter oauth(String failUrl);
	
}
