package com.ranhfun.jpa.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

public class FilterManagerImpl implements FilterManager {

	private final Map<String, List<Parameter>> parameters = CollectionFactory.newMap();
	
	public FilterManagerImpl(final Map<String, Parameter> configuration) {
		for (Map.Entry<String, Parameter> entry : configuration.entrySet()) {
			List<Parameter> values = null;
			if (parameters.containsKey(entry.getKey())) {
				values = parameters.get(entry.getKey());
			} else {
				values = CollectionFactory.newList();
				parameters.put(entry.getKey(), values);
			}
			values.add(entry.getValue());
		}
	}

	public Map<String, List<Parameter>> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	
}
