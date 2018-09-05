package com.ranhfun.activemq.app2.services;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.broker.BrokerService;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.activemq.ActiveMQSymbols;
import com.ranhfun.activemq.annotations.ConnectionContext;
import com.ranhfun.activemq.internal.MQConnectionObjectProvider;
import com.ranhfun.activemq.services.ActiveMQModule;
import com.ranhfun.activemq.services.BrokerServiceManager;
import com.ranhfun.activemq.services.MQConnectionFactoryManager;
import com.ranhfun.activemq.services.MQConnectionFactorySource;

@SubModule(ActiveMQModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }
	
    @Contribute(BrokerServiceManager.class)
    public static void contributeBrokerServiceManager(
            final OrderedConfiguration<BrokerService> configuration)
    {
    	BrokerService service = new BrokerService();
        service.setUseJmx(true);
        service.setPersistent(false);
        //service.setDataDirectory("activemq-data2");
        try {
			service.addConnector("tcp://localhost:61276");
		} catch (Exception e) {
			// should never occur
		}
		configuration.add("test2", service);
    }  
    
    @Contribute(MQConnectionFactorySource.class)
    public static void contributeMQConnectionFactorySource(
            final MappedConfiguration<String, String> configuration)
    {
        //configuration.add("test1", "vm://localhost?broker.persistent=false");
    	configuration.add("test2", "tcp://localhost:61276");
    }
    
/*    @Startup
    public static void messageListener(	
    		@ConnectionContext("test2")
    		Connection connection,
    		RegistryShutdownHub registryShutdownHub)
    {
		Session session;
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("TEST.QUEUE");
			final MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				
				public void onMessage(Message message) {
					try {
						MapMessage mapMessage = (MapMessage)message;
						System.out.println("message... " + mapMessage.getString("figureSeq") + " " + mapMessage.getDouble("localPrice1")
								 + " " + mapMessage.getDouble("localPrice2")  + " " + mapMessage.getDouble("localPrice3"));
					} catch (JMSException e) {
						// should never occur
					}
				}
			});
			registryShutdownHub.addRegistryShutdownListener(new Runnable() {
				public void run() {
					try {
						consumer.close();
					} catch (JMSException e) {
						// should never occur
					}
				}
			});
		} catch (JMSException e) {
			// should never occur
		}
    }*/
}
