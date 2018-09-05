package com.ranhfun.swagger.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.tynamo.resteasy.ResteasySymbols;

import com.ranhfun.swagger.SwaggerConstants;
import com.ranhfun.swagger.services.SwaggerModule;

@SubModule(SwaggerModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	    configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	    configuration.add(SwaggerConstants.SWAGGER_BASE_PATH_FLAG, "true");
	    configuration.add(SwaggerConstants.SWAGGER_BASE_PATH, "http://localhost:8001/a_v1/api");
    }
	
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
	{
		configuration.add(ResteasySymbols.MAPPING_PREFIX, "/api");
	}
	
	public static void contributeResteasyPackageManager(Configuration<String> configuration)
	{
		configuration.add("com.ranhfun.swagger.ws");
	}
}
