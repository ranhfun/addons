package com.ranhfun.jpa.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.jpa.EntityManagerSource;
import org.hibernate.event.spi.EventType;

public class ListenerManagerImpl2 implements ListenerManager {

	private final Map<EventType, Object[]> listeners = CollectionFactory.newMap();
	
	public ListenerManagerImpl2(final Map<EventType, Object> configuration, EntityManagerSource entityManagerSource) {
		for (Map.Entry<EventType, Object> entry : configuration.entrySet()) {
			if (listeners.containsKey(entry.getKey())) {
				List<Object> objects = Arrays.asList(listeners.get(entry.getKey()));
				objects.add(entry.getValue());
				listeners.put(entry.getKey(), objects.toArray());
			} else {
				listeners.put(entry.getKey(), new Object[]{entry.getValue()});
			}
		}
	}

	public Map<EventType, Object[]> getListeners() {
		return listeners;
	}
	
}
