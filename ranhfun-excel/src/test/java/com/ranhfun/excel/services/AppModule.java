package com.ranhfun.excel.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.excel.services.ExcelModule;

@SubModule(ExcelModule.class)
public class AppModule {

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	}
	
}
