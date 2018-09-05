package com.ranhfun.activemq.services;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;

public interface MQConnectionFactoryManager
{
    Connection getConnection(String name) throws JMSException;

    Map<String, Connection> getConnections() throws JMSException;
}
