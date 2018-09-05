package com.ranhfun.jpa.services;

import java.util.List;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.hibernate.Interceptor;

public class InterceptorManagerImpl implements InterceptorManager {

	private List<InterceptorListener> listeners = CollectionFactory.newList();
	
	public InterceptorManagerImpl(List<InterceptorListener> listeners) {
		this.listeners.addAll(listeners);
	}
	
	public void run(Interceptor interceptor) {
		for (InterceptorListener listener : listeners) {
			listener.afterInteceptorInit(interceptor);
		}
	}

}
