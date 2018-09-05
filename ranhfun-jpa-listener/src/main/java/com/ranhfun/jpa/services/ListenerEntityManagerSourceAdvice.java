package com.ranhfun.jpa.services;

import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class ListenerEntityManagerSourceAdvice implements MethodAdvice {

	private final ListenerManager listenerManager;
	
	private boolean listenerFlag;
	
	public ListenerEntityManagerSourceAdvice(ListenerManager listenerManager) {
		this.listenerManager = listenerManager;
	}

	public void advise(MethodInvocation invocation) {
		invocation.proceed();
		if (!listenerFlag) {
			Map<String, EntityManager> ems = (Map<String, EntityManager>) invocation.getReturnValue();
			if (ems == null) return;
			for (EntityManager em : ems.values()) {
				ServiceRegistryImplementor serviceRegistry = em.unwrap(SessionImplementor.class).getFactory().getServiceRegistry();
				EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
				for (Map.Entry<EventType, Object[]> entry : listenerManager.getListeners().entrySet()) {
					eventListenerRegistry.appendListeners(entry.getKey(), entry.getValue());
				}
			}
			listenerFlag = true;
		}
	}

}
