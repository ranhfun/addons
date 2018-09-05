package com.ranhfun.activemq.services;

import java.util.List;

import javax.jms.JMSException;

import org.apache.activemq.broker.BrokerService;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.slf4j.Logger;

import com.ranhfun.activemq.ActiveMQSymbols;
import com.ranhfun.activemq.internal.ConnectionContextWorker;
import com.ranhfun.activemq.internal.MQConnectionObjectProvider;

public class ActiveMQModule {

    public static void bind(final ServiceBinder binder)
    {
        binder.bind(MQConnectionFactorySource.class, MQConnectionFactorySourceImpl.class);
    }
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(final MappedConfiguration<String, String> configuration)
    {
        configuration.add(ActiveMQSymbols.EARLY_START_UP, "true");
    }
    
    public static MQConnectionFactoryManager buildMQConnectionFactoryManager(
    		final MQConnectionFactorySource connectionFactorySource, 
    		final RegistryShutdownHub registryShutdownHub,
    		final Logger logger)
    {
        final MQConnectionFactoryManagerImpl service = new MQConnectionFactoryManagerImpl(connectionFactorySource, logger);

        registryShutdownHub.addRegistryWillShutdownListener(service);

        return service;
    }
    
    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideClassTransformWorkers(OrderedConfiguration<ComponentClassTransformWorker2> configuration)
    {
        configuration.addInstance("ConnectionContext", ConnectionContextWorker.class, "after:Property");
    }    
    
    @Contribute(MasterObjectProvider.class)
    public static void provideObjectProviders(final OrderedConfiguration<ObjectProvider> configuration)
    {
        configuration.addInstance("MQConnection", MQConnectionObjectProvider.class,
                "before:AnnotationBasedContributions");
    }
    
    public static BrokerServiceManager buildBrokerServiceManager(final List<BrokerService> brokerServices)
    {
        return new BrokerServiceManager()
        {
			public List<BrokerService> getBrokerServices() {
				return brokerServices;
			}
        };
    }
    
    @Startup
    public static void startupEarly(final MQConnectionFactoryManager connectionFactoryManager, 
    		@Symbol(ActiveMQSymbols.EARLY_START_UP)
    		final boolean earlyStartup,
    		final BrokerServiceManager brokerServiceManager,
    		final RegistryShutdownHub registryShutdownHub,
    		final Logger logger)
    {
    	for (final BrokerService brokerService : brokerServiceManager.getBrokerServices()) {
    		try {
				brokerService.start();
				brokerService.waitUntilStarted();
				registryShutdownHub.addRegistryShutdownListener(new Runnable() {
					public void run() {
						try {
							brokerService.stop();
							brokerService.waitUntilStopped();
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("brokerService not corrent stop " + brokerService.getBrokerName(), e);
						}
					}
				});
        	} catch (Exception e) {
        		e.printStackTrace();
        		logger.error("brokerService not corrent startup " + brokerService.getBrokerName(), e);
    		}
		}
    	
		/*registryShutdownHub.addRegistryShutdownListener(new Runnable() {
			public void run() {
				logger.info("Doing amq static shutdown.");
			    try {
			      DefaultThreadPools.getDefaultTaskRunnerFactory().shutdown();
			    } finally {
			      DefaultThreadPools.shutdown();
			      logger.info("Finally call amq static shutdown."); 
			    }
			}
		});*/
    	
        if (!earlyStartup)
            return;

        try {
			connectionFactoryManager.getConnections();
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("not corrent earlyStartup", e);
		}
    }
}
