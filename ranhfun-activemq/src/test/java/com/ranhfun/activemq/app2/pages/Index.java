package com.ranhfun.activemq.app2.pages;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ranhfun.activemq.annotations.ConnectionContext;
import com.ranhfun.activemq.services.MQConnectionFactoryManager;

public class Index {
	
    protected Destination consumerDestination;
    protected Destination producerDestination;
	
	@SuppressWarnings("unused")
	@Inject
	private MQConnectionFactoryManager connectionFactoryManager;
	
	@ConnectionContext("test2")
	private Connection connection;
	
	@Property
	private String message;
	
	void onActivate() throws JMSException {
		//Session session = connectionFactoryManager.getConnection("test1").createSession(false, Session.AUTO_ACKNOWLEDGE);
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("TEST.QUEUE");
		MessageConsumer consumer = session.createConsumer(destination);
		TextMessage message = (TextMessage)consumer.receive(10000);
		if (message!=null) {
			this.message = message.getText();
		}
		consumer.close();
		session.close();
	}
	
}
