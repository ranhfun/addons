package com.ranhfun.jpa.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Order;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.jpa.EntityManagerSource;
import org.hibernate.EmptyInterceptor;

import com.ranhfun.jpa.InterceptorConstants;

public class InterceptorModule {

    public static void bind(final ServiceBinder binder)
    {
        binder.bind(InterceptorManager.class, InterceptorManagerImpl.class);
    }
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(InterceptorConstants.INTERCEPTOR_CLASS, EmptyInterceptor.class.getName());
    }
    
	@SuppressWarnings("unchecked")
	@Advise(serviceInterface = EntityManagerSource.class)
	@Order("before:*")
	public static void secureEntityManager(final MethodAdviceReceiver receiver,
		@Autobuild InterceptorEntityManagerSourceAdvice advice) throws SecurityException, NoSuchMethodException {
		receiver.adviseMethod(receiver.getInterface().getMethod("create", new Class[] { String.class }), advice);
	}
	
}
