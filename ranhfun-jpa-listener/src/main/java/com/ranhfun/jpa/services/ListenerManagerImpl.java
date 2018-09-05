package com.ranhfun.jpa.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.jpa.EntityManagerSource;
import org.hibernate.event.spi.EventType;

public class ListenerManagerImpl implements ListenerManager {

	private final Map<EventType, Object[]> listeners = CollectionFactory.newMap();
	
	public ListenerManagerImpl(final List<JpaListener> configuration, EntityManagerSource entityManagerSource) {
		for (JpaListener listener : configuration) {
			if (listeners.containsKey(listener.getType())) {
				List<Object> objects = new ArrayList<Object>(Arrays.asList(listeners.get(listener.getType())));
				objects.add(listener.getListener());
				listeners.put(listener.getType(), objects.toArray());
			} else {
				listeners.put(listener.getType(), new Object[]{listener.getListener()});
			}
		}
	}

	public Map<EventType, Object[]> getListeners() {
		return listeners;
	}
	
}
