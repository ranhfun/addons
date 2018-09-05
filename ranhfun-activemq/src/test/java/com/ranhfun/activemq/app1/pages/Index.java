package com.ranhfun.activemq.app1.pages;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
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
	
	@Inject
	private MQConnectionFactoryManager connectionFactoryManager;
	
	@ConnectionContext("test1")
	private Connection connection;
	
	@Property
	private String message;
	
	void onActivate() throws JMSException {
		//Session session = connectionFactoryManager.getConnection("test1").createSession(false, Session.AUTO_ACKNOWLEDGE);
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("TEST.QUEUE");
		MessageProducer producer = session.createProducer(destination);
		MessageConsumer consumer = session.createConsumer(destination);
		producer.send(session.createTextMessage("test1"));
		producer.send(session.createTextMessage("test2"));
		producer.send(session.createTextMessage("test3"));
		//TextMessage message = (TextMessage)consumer.receive(10000);
		this.message = "tests";
		
		// map message
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("name", "value");
		
		consumer.close();
		producer.close();
		session.close();
		

	}
}
