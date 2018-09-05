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

package com.ranhfun.activemq.internal;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;

import com.ranhfun.activemq.annotations.ConnectionContext;
import com.ranhfun.activemq.services.MQConnectionFactoryManager;

public class ActiveMQInternalUtils
{

    public static Connection getConnection(MQConnectionFactoryManager connectionFactoryManager,
                                                 ConnectionContext annotation)
    {
        String name = annotation == null ? null : annotation.value();

        try {
        
        if (InternalUtils.isNonBlank(name))
				return connectionFactoryManager.getConnection(name);
        
        Map<String, Connection> connections = connectionFactoryManager.getConnections();

        if (connections.size() == 1)
            return connections.values().iterator().next();

		} catch (JMSException e) {
			throw new RuntimeException("locate MQConnection fail.", e); 
		}
        
        throw new RuntimeException("Unable to locate a single Connection. " +
                "You must provide the factory name using the @ConnectionContext annotation.");
    }
}
