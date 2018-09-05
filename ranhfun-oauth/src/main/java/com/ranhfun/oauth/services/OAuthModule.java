package com.ranhfun.oauth.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;
import org.tynamo.resteasy.ResteasyModule;

import com.ranhfun.oauth.OAuthResponse;
import com.ranhfun.oauth.filter.OAuthFilter;

@SubModule({ResteasyModule.class})
public class OAuthModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(OAuthProvider.class, OAuthMemoryProvider.class);
		binder.bind(OAuthValidator.class);
		binder.bind(HttpServletRequestFilter.class, OAuthFilter.class).withId("OAuthFilter");
	}

	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
		configuration.addInstance(OAuthResponse.class, OAuthResponseResultProcessor.class);
	}
	
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
    	configuration.add(new LibraryMapping("oauth", "com.ranhfun.oauth"));
    }	
    
	public static void contributeHttpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
            @InjectService("OAuthFilter") HttpServletRequestFilter oauthRequestFilter)
	{
		configuration.add("OAuthFilter", oauthRequestFilter, "after:IgnoredPaths","before:ResteasyRequestFilter");
	}    
}
