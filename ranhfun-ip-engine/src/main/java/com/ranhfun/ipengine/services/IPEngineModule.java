package com.ranhfun.ipengine.services;

import org.apache.tapestry5.ioc.ServiceBinder;

public class IPEngineModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(IPSeeker.class);
	}
	
}
