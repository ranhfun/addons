package com.ranhfun.jpa.services;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;
import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;
import org.hibernate.event.spi.EventType;

//@UsesMappedConfiguration(key=EventType.class,value=Object.class)
@UsesOrderedConfiguration(JpaListener.class)
public interface ListenerManager {
	
	public Map<EventType, Object[]> getListeners();
	
}
