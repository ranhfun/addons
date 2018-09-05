package com.ranhfun.oauth2.services;

import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

public class OAuthModule {

	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
		configuration.addInstance(OAuthResponse.class, OAuthResponseResultProcessor.class);
	}
	
}
