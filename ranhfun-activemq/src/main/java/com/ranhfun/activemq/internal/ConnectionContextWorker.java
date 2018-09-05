package com.ranhfun.activemq.internal;

import org.apache.tapestry5.internal.transform.ReadOnlyComponentFieldConduit;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.InstanceContext;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.ranhfun.activemq.annotations.ConnectionContext;
import com.ranhfun.activemq.services.MQConnectionFactoryManager;

public class ConnectionContextWorker implements ComponentClassTransformWorker2
{
    private final MQConnectionFactoryManager connectionFactoryManager;

    public ConnectionContextWorker(final MQConnectionFactoryManager connectionFactoryManager)
    {
        this.connectionFactoryManager = connectionFactoryManager;
    }

    public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model)
    {
        for (final PlasticField field : plasticClass
                .getFieldsWithAnnotation(ConnectionContext.class))
        {
            final ConnectionContext annotation = field.getAnnotation(ConnectionContext.class);

            field.claim(annotation);

            field.setConduit(new ReadOnlyComponentFieldConduit(plasticClass.getClassName(), field.getName())
            {
                public Object get(Object instance, InstanceContext context)
                {
                    return ActiveMQInternalUtils.getConnection(connectionFactoryManager, annotation);
                }
            });
        }
    }
}
