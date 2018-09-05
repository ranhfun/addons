package com.ranhfun.swagger.services;

import java.util.HashSet;
import java.util.Set;
import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolSource;

import com.ranhfun.swagger.SwaggerConstants;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

public class Application extends javax.ws.rs.core.Application
{
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();


	public Application(Collection<Object> singletons, 
			@Symbol(SwaggerConstants.SWAGGER_VERSION)String version, 
			@Symbol(SwaggerConstants.SWAGGER_RESOURCE_PACKAGE)String resourcePackage,
			@Symbol(SwaggerConstants.SWAGGER_BASE_PATH_FLAG)boolean basePathFlag,
			@Inject SymbolSource symbolSource)
	{
		this.singletons = new HashSet<Object>(singletons);
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(version);
        beanConfig.setResourcePackage(resourcePackage);
        beanConfig.setScan(true);
        if (basePathFlag) {
        	beanConfig.setBasePath(symbolSource.valueForSymbol(SwaggerConstants.SWAGGER_BASE_PATH));
		}
	}

	@Override
	public Set<Class<?>> getClasses()
	{
		return empty;
	}

	@Override
	public Set<Object> getSingletons()
	{
		return singletons;
	}
}
