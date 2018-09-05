package com.ranhfun.resteasy.services;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.HttpServletRequestFilter;

public class ResteasyFilterModule {

	public static void bind(final ServiceBinder binder)
	{
		binder.bind(HttpServletRequestFilter.class, ResteasyUT8Filter.class).withId("ResteasyUT8Filter");
	}
	
	public static void contributeHttpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
			@InjectService("ResteasyUT8Filter") HttpServletRequestFilter resteasyUT8Filter) {
			configuration.add("ResteasyUT8Filter", resteasyUT8Filter, "after:IgnoredPaths", "before:ResteasyRequestFilter");
	}
}
