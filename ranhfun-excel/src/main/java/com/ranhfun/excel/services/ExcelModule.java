package com.ranhfun.excel.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import com.ranhfun.excel.ExcelResponse;

public class ExcelModule {

	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
		configuration.addInstance(ExcelResponse.class, ExcelResponseResultProcessor.class);
	}
	
}
