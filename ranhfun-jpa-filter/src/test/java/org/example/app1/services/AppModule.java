package org.example.app1.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.jpa.services.FilterManager;
import com.ranhfun.jpa.services.FilterModule;
import com.ranhfun.jpa.services.Parameter;

@SubModule(FilterModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }

    @Contribute(FilterManager.class)
    public static void provideFilterManager(
            final MappedConfiguration<String, Parameter> configuration)
    {
    	configuration.add("byName", new Parameter("name", "%1%"));
    }
    
}
