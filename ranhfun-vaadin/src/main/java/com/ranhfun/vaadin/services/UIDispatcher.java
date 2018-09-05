package com.ranhfun.vaadin.services;

import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.ui.UI;

@UsesOrderedConfiguration(UIDispatcher.class)
public interface UIDispatcher {

	public Class<? extends UI> dispatcher(UIClassSelectionEvent event);
	
}
