package com.ranhfun.cas.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;
import org.tynamo.security.services.SecurityModule;

import com.ranhfun.cas.CasSymbols;

@SubModule(SecurityModule.class)
public class CASModule {

	public static void bind(final ServiceBinder binder)
	{
		binder.bind(CasSecurityFilterChainFactory.class, CasSecurityFilterChainFactoryImpl.class);
		binder.bind(HttpServletRequestFilter.class, CasSsoLogoutFilter.class).withId("CasSsoLogoutFilter");
	}
	
	public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
	{
		configuration.add(CasSymbols.CAS_SERVER_URL_PREFIX, "https://server.cas.com/");
		configuration.add(CasSymbols.CAS_SERVICE, "http://application.examples.com/shiro-cas");
		configuration.add(CasSymbols.CAS_FAILURE_URL, "${cas.server.url.prefix}?service=${cas.service}");
		configuration.add(CasSymbols.CAS_LOGOUT_URL, "${cas.server.url.prefix}/logout?service=${cas.service}");
		configuration.add(CasSymbols.CAS_SSO_LOGOUT_FILTER, "false");
	}

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("cas", "com.ranhfun.cas"));
    }	
    
	public static void contributeHttpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
			@InjectService("CasSsoLogoutFilter") HttpServletRequestFilter casSsoConfiguration,
			@Symbol(CasSymbols.CAS_SSO_LOGOUT_FILTER)
		    final boolean logoutFilter) {
		if (logoutFilter) {
			configuration.add("CasSsoConfiguration", casSsoConfiguration, "before:SecurityConfiguration", "after:StoreIntoGlobals");
		}
	}
}
