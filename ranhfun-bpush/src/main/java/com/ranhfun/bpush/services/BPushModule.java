package com.ranhfun.bpush.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.bpush.BPushConstants;

public class BPushModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(BPushService.class, BPushServiceImpl.class);
	}
	
	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void provideFactoryDefaults(
			final MappedConfiguration<String, String> configuration) {
		configuration.add(BPushConstants.BPUSH_API_KEY, "rfZGcbh9awPkkeoEqeKLgro6");
		configuration.add(BPushConstants.BPUSH_SECRET_KEY, "pqAIEGdEu6pKrpUXSYV7nUxb6jY4bjwO");
	}
	
}
