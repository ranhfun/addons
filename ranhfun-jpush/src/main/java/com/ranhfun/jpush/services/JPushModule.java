package com.ranhfun.jpush.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.jpush.JPushConstants;

public class JPushModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(JPushService.class, JPushServiceImpl.class);
	}
	
	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void provideFactoryDefaults(
			final MappedConfiguration<String, String> configuration) {
		configuration.add(JPushConstants.JPUSH_API_KEY, "3827c3bf48a44386d6a51a34");
		configuration.add(JPushConstants.JPUSH_SECRET_KEY, "ec28c89945f5e7a48e41956d");
	}
	
}
