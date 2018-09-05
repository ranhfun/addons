package com.ranhfun.eventbus.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.common.eventbus.AsyncEventBus;
import com.ranhfun.common.eventbus.EventBus;
import com.ranhfun.eventbus.services.EventBusModule;

@SubModule(EventBusModule.class)
public class AppModule {

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	}
	
	@Contribute(EventBus.class)
	public static void contributEventBusListener(OrderedConfiguration<Object> configuration) {
		configuration.add("Number", new IntegerListener());
	}
	
	@Contribute(AsyncEventBus.class)
	public static void contributAsyncEventBusListener(OrderedConfiguration<Object> configuration) {
		configuration.add("Number", new IntegerListener());
	}
	
}
