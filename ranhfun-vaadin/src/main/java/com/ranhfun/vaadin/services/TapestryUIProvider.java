package com.ranhfun.vaadin.services;

import org.apache.tapestry5.ioc.ObjectLocator;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class TapestryUIProvider extends UIProvider {

	private ObjectLocator locator;
	private UIDispatcher dispatcher;
	
	public TapestryUIProvider(ObjectLocator locator, UIDispatcher dispatcher) {
		this.locator = locator;
		this.dispatcher = dispatcher;
	}
	
	public UI createInstance(UICreateEvent event) {
		return locator.autobuild(event.getUIClass());
	}

	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
    	return dispatcher.dispatcher(event);
    }

}
