package org.example.soup.asset.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestFilter;
import org.apache.tapestry5.services.PageRenderRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.RequestGlobals;
import org.example.soup.filter.LoginFilter;

import com.ranhfun.soup.asset.services.FileAssetModule;

@SubModule(FileAssetModule.class)
public class AppModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(PageRenderRequestFilter.class,LoginFilter.class).withId("LoginFilter");
	}
	
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "0.0.1");
    }
	
	/*public void contributePageRenderRequestHandler( 
            OrderedConfiguration<PageRenderRequestFilter> configuration,PageRenderRequestFilter loginFilter) 
    { 
        configuration.add("LoginFilter", loginFilter); 
    } */
	
}
