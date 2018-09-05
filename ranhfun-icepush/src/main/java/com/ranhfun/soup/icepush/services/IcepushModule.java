package com.ranhfun.soup.icepush.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;
import org.icepush.PushContext;
import org.icepush.servlet.PseudoServlet;

import com.ranhfun.soup.icepush.filter.IcepushRequestFilter;
import com.ranhfun.soup.icepush.timer.GroupIntervalTimer;

public class IcepushModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(PushContextHolder.class, PushContextHolderImpl.class);
		binder.bind(HttpServletRequestFilter.class, IcepushRequestFilter.class)
				.withId("IcepushRequestFilter");
	}
/*	
	//@Scope(ScopeConstants.PERTHREAD)
	@EagerLoad
	public static PseudoServlet buildPseudoServlet(final ApplicationGlobals applicationGlobals,
			RegistryShutdownHub hub) {
		final TapestryPseudoServlet pseudoServlet = new TapestryPseudoServlet(applicationGlobals);
		hub.addRegistryShutdownListener(new Runnable() {
			public void run() {
				pseudoServlet.shutdown();
			}
		});
		return pseudoServlet;
	}*/
	
	public static PushContext buildPushContext(PushContextHolder pushContextHolder) {
		return pushContextHolder.get();
	}	

	public static void contributeHttpServletRequestHandler(
			OrderedConfiguration<HttpServletRequestFilter> configuration,
			@InjectService("IcepushRequestFilter") 
			HttpServletRequestFilter icepushRequestFilter) {
		configuration.add("IcepushRequestFilter", icepushRequestFilter, "before:GZIP");
	}

	
	public static GroupIntervalTimer buildGroupIntervalTimer(PushContextHolder pushContextHolder, RegistryShutdownHub hub) {
		GroupIntervalTimer groupIntervalTimer = new GroupIntervalTimer(pushContextHolder);
		hub.addRegistryShutdownListener(groupIntervalTimer);
		return groupIntervalTimer;
	}
	
	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
	{
		configuration.add(new LibraryMapping("icep", "com.ranhfun.soup.icepush"));
	}
}
