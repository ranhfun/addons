package com.ranhfun.resteasy.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.tynamo.resteasy.ResteasySymbols;

public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(ResteasySymbols.MAPPING_PREFIX, "/api");
    }
    
	public static void contributeResteasyPackageManager(Configuration<String> configuration)
	{
		configuration.add("com.ranhfun.resteasy.ws");
		//configuration.add("com.ranhfun.resteasy.plugins");
	}
	
}
