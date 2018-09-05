package com.ranhfun.jpa.services;

import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;
import org.hibernate.Interceptor;

@UsesOrderedConfiguration(InterceptorListener.class)
public interface InterceptorManager {

	public void run(Interceptor interceptor);
	
}
