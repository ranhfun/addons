package com.ranhfun.util.services;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;

@UsesConfiguration(String.class)
public interface TransformService {

	public boolean isEditorPage(String logicalPageName);
	
	public Collection<String> getValues();
	
}
