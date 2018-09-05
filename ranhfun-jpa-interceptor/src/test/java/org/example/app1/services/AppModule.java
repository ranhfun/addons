package org.example.app1.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.hibernate.Interceptor;

import com.ranhfun.jpa.InterceptorConstants;
import com.ranhfun.jpa.services.InterceptorListener;
import com.ranhfun.jpa.services.InterceptorManager;
import com.ranhfun.jpa.services.InterceptorModule;

@SubModule(InterceptorModule.class)
public class AppModule {

    public static void bind(final ServiceBinder binder)
    {
        binder.bind(TestService.class, TestServiceImpl.class);
    }
	
    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(InterceptorConstants.INTERCEPTOR_CLASS, TestInterceptor.class.getName());
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }
	
    @Contribute(InterceptorManager.class)
    public static void provideListenerManager(
            final OrderedConfiguration<InterceptorListener> configuration,
            final TestService testService)
    {
    	configuration.add("Test", new InterceptorListener() {
			
			public void afterInteceptorInit(Interceptor interceptor) {
				TestInterceptor testInterceptor = (TestInterceptor)interceptor;
				testInterceptor.setTestService(testService);
			}
		});
    }
    
}
