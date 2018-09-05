package com.ranhfun.vaadin.services;

import java.util.List;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

import com.ranhfun.vaadin.VaadinSymbols;
import com.vaadin.server.UIProvider;

public class VaadinModule {

    public static void bind(ServiceBinder binder)
    {
      binder.bind(UIProvider.class, TapestryUIProvider.class);
      binder.bind(HttpServletRequestFilter.class, VaadinRequestFilter.class).withId("VaadinRequestFilter");
    }
	
    public UIDispatcher buildUIDispatcher(ChainBuilder chainBuilder, List<UIDispatcher> configuration)
    {
        return chainBuilder.build(UIDispatcher.class, configuration);
    }
    
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(VaadinSymbols.MAPPING_PREFIX, "/v");
    }
    
	@Contribute(HttpServletRequestHandler.class)
	public static void httpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
	                                             @InjectService("VaadinRequestFilter")
	                                             HttpServletRequestFilter resteasyRequestFilter)
	{
		configuration.add("VaadinRequestFilter", resteasyRequestFilter, "after:IgnoredPaths", "before:GZIP");
	}
	
   /* public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("VAADIN.themes.base", "VAADIN/themes/base");
    }*/
	
}
