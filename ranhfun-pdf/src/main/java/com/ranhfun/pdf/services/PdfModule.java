package com.ranhfun.pdf.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import com.ranhfun.pdf.PdfResponse;

public class PdfModule {

	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
		configuration.addInstance(PdfResponse.class, PdfResponseResultProcessor.class);
	}
	
}
