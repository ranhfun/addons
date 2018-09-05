package com.ranhfun.activemq.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

public class MQConnectionFactorySourceImpl implements MQConnectionFactorySource
{
    private final Map<String, String> configuration;
    private final Map<String, ConnectionFactory> connectionFactories = CollectionFactory.newMap();

    public MQConnectionFactorySourceImpl(Map<String, String> configuration)
    {
    	this.configuration = CollectionFactory.newMap(configuration);
    }

    public ConnectionFactory getConnectionFactory(final String name)
    {
    	ConnectionFactory conf = connectionFactories.get(name);

        if (conf == null)
        {
            conf = new ActiveMQConnectionFactory(configuration.get(name));

            connectionFactories.put(name, conf);
        }

        return conf;
    }

    public Connection create(final String name) throws JMSException
    {
        return getConnectionFactory(name).createConnection();
    }

    public List<String> getNames()
    {
        return Collections.unmodifiableList(Arrays.asList(configuration.keySet().toArray(new String[configuration.size()])));
    }

}
