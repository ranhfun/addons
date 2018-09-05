package com.ranhfun.swagger.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

import com.ranhfun.swagger.SwaggerConstants;

public class SwaggerModule {

	public static void bind(ServiceBinder binder)
	{
		binder.bind(javax.ws.rs.core.Application.class, com.ranhfun.swagger.services.Application.class).withId("SwaggerApplication");
	}

	@Contribute(HttpServletRequestHandler.class)
	public static void setupApplicationServiceOverrides(OrderedConfiguration<HttpServletRequestFilter> configuration) {
		configuration.overrideInstance("ResteasyRequestFilter", ResteasyRequestFilter.class, "after:IgnoredPaths", "before:GZIP");
	}
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SwaggerConstants.SWAGGER_VERSION, "${" + SymbolConstants.APPLICATION_VERSION + "}");
        configuration.add(SwaggerConstants.SWAGGER_RESOURCE_PACKAGE, "${" + InternalConstants.TAPESTRY_APP_PACKAGE_PARAM + "}");
        configuration.add(SwaggerConstants.SWAGGER_BASE_PATH_FLAG, "false");
    }
	
	public static void contributeResteasyPackageManager(Configuration<String> configuration)
	{
		configuration.add("com.wordnik.swagger.jaxrs.json");
		configuration.add("com.wordnik.swagger.jaxrs.listing");
	}
}
