package com.ranhfun.oauth2.services;

import org.apache.tapestry5.ioc.ServiceBinder;

public class OAuthFilterModule {

	public static void bind(final ServiceBinder binder)
	{
		binder.bind(OAuthSecurityFilterChainFactory.class, OAuthSecurityFilterChainFactoryImpl.class);
	}
	
}
