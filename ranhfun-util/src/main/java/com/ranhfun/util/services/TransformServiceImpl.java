package com.ranhfun.util.services;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

public class TransformServiceImpl implements TransformService {

	public Collection<String> values;

	public TransformServiceImpl(final Collection<String> values) {
		this.values = values;
	}

	public boolean isEditorPage(String logicalPageName) {
		for (String value : values) {
			if (StringUtils.equalsIgnoreCase(logicalPageName, value)) {
				return true;
			}
		}
		return false;
	}

	public Collection<String> getValues() {
		return values;
	}

}
