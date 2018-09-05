// Copyright 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.ranhfun.activemq.services;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.slf4j.Logger;

public class MQConnectionFactoryManagerImpl implements MQConnectionFactoryManager, Runnable
{
    private final MQConnectionFactorySource connectionFactorySource;

    private final Logger logger;

    private final Map<String, Connection> connections = CollectionFactory.newMap();

    public MQConnectionFactoryManagerImpl(final MQConnectionFactorySource connectionFactorySource,
            final Logger logger)
    {
        super();
        this.connectionFactorySource = connectionFactorySource;
        this.logger = logger;
    }

    public Connection getConnection(final String name) throws JMSException
    {
        return getOrCreateConnection(name);
    }

    public Map<String, Connection> getConnections() throws JMSException
    {
        createAllConnections();
        
        return Collections.unmodifiableMap(connections);
    }

    private void createAllConnections() throws JMSException
    {
        for (final String name : connectionFactorySource.getNames())
        {
            getOrCreateConnection(name);
        }
    }

    private Connection getOrCreateConnection(final String name) throws JMSException
    {
        Connection con = connections.get(name);

        if (con == null)
        {
        	con = connectionFactorySource.create(name);
        	
        	con.start();

            connections.put(name, con);
        }

        return con;
    }

	public void run() {
        for (final Entry<String, Connection> next : connections.entrySet())
        {
            try
            {
                final Connection con = next.getValue();
                
                con.stop();
                
                con.close();
                
            }
            catch (final Exception e)
            {
                logger.info(String.format(
                        "Failed to close MQConnection for unit '%s'", next.getKey()));
            }
        }

        connections.clear();
	}
}
