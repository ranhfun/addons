package com.ranhfun.activemq.internal;

import javax.jms.Connection;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;

import com.ranhfun.activemq.annotations.ConnectionContext;
import com.ranhfun.activemq.services.MQConnectionFactoryManager;

public class MQConnectionObjectProvider implements ObjectProvider
{

    private Connection proxy;

    public <T> T provide(final Class<T> objectType, final AnnotationProvider annotationProvider,
            final ObjectLocator locator)
    {
        if (objectType.equals(Connection.class))
            return objectType.cast(getOrCreateProxy(annotationProvider, locator));

        return null;
    }

    private synchronized Connection getOrCreateProxy(
            final AnnotationProvider annotationProvider, final ObjectLocator objectLocator)
    {
        if (proxy == null)
        {
            PlasticProxyFactory proxyFactory = objectLocator.getService("PlasticProxyFactory",
                    PlasticProxyFactory.class);

             final ConnectionContext annotation = annotationProvider
                            .getAnnotation(ConnectionContext.class);

            proxy = proxyFactory.createProxy(Connection.class, new ObjectCreator<Connection>()
            {
                public Connection createObject()
                {
                    final MQConnectionFactoryManager factoryManagerManager = objectLocator
                            .getService(MQConnectionFactoryManager.class);

						return ActiveMQInternalUtils.getConnection(factoryManagerManager, annotation);
                }
            }, "<MQConnectionManagerProxy>");
        }

        return proxy;
    }

}
