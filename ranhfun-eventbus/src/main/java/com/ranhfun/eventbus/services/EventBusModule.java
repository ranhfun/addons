package com.ranhfun.eventbus.services;

import java.util.List;
import java.util.concurrent.Executors;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.common.eventbus.AsyncEventBus;
import com.ranhfun.common.eventbus.EventBus;
import com.ranhfun.eventbus.EventBusConstants;

public class EventBusModule {

    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(EventBusConstants.EVENTBUS_THEAD_POOL_SIZE, "3");
    }
	
	public static EventBus buildEventBus(List<Object> registers) {
		EventBus eventBus = new EventBus();
		for (Object object : registers) {
			eventBus.register(object);
		}
		return eventBus;
	}
	
	public static AsyncEventBus buildAsyncEventBus(List<Object> registers, @Symbol(EventBusConstants.EVENTBUS_THEAD_POOL_SIZE) int poolSize) {
		AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(poolSize));
		for (Object object : registers) {
			eventBus.register(object);
		}
		return eventBus;
	}
}
