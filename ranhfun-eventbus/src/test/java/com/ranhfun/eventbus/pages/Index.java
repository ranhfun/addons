package com.ranhfun.eventbus.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ranhfun.common.eventbus.AsyncEventBus;
import com.ranhfun.common.eventbus.EventBus;
import com.ranhfun.common.eventbus.EventBusException;

public class Index {

	@Inject
	private EventBus eventBus;
	
	@Inject
	private AsyncEventBus asyncEventBus;
	
	void setupRender() throws EventBusException {
		eventBus.post(new Integer(12));
		for (int i = 0; i < 100; i++) {
			asyncEventBus.post(new Integer(i));
		}
	}
	
}
