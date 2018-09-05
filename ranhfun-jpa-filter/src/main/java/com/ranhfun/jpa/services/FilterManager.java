package com.ranhfun.jpa.services;

import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;

@UsesMappedConfiguration(Parameter.class)
public interface FilterManager {
	
	public Map<String, List<Parameter>> getParameters();
	
}
