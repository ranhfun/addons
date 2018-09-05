package com.ranhfun.zip.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import com.ranhfun.zip.ZipResponse;


public class ZipModule {

	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
		configuration.addInstance(ZipResponse.class, ZipResponseResultProcessor.class);
	}
	
}
