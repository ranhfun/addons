package com.ranhfun.jpa.services;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.jpa.EntityManagerManager;

public class ListenerModule {

	public static void bind(ServiceBinder binder)
    {
		binder.bind(ListenerManager.class, ListenerManagerImpl.class);
    }
	
	@SuppressWarnings("unchecked")
	@Advise(serviceInterface = EntityManagerManager.class)
	public static void listenerEntityManager(final MethodAdviceReceiver receiver,
		@Autobuild ListenerEntityManagerSourceAdvice advice) throws SecurityException, NoSuchMethodException {
		receiver.adviseMethod(receiver.getInterface().getMethod("getEntityManagers"), advice);
	}
	
}
