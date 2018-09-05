package com.ranhfun.jpa.services;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.jpa.EntityManagerSource;

public class FilterModule {

    public static void bind(final ServiceBinder binder)
    {
        binder.bind(FilterManager.class, FilterManagerImpl.class);
    }
	
	@SuppressWarnings("unchecked")
	@Advise(serviceInterface = EntityManagerSource.class)
	public static void filterEntityManager(final MethodAdviceReceiver receiver,
		@Autobuild FilterEntityManagerSourceAdvice advice) throws SecurityException, NoSuchMethodException {
		receiver.adviseMethod(receiver.getInterface().getMethod("create", new Class[] { String.class }), advice);
	}
	
}
