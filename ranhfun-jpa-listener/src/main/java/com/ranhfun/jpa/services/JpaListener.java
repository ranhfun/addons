package com.ranhfun.jpa.services;

import org.hibernate.event.spi.EventType;

public class JpaListener {

	private EventType type;
	
	private Object listener;

	public JpaListener() {}
	
	public JpaListener(EventType type, Object listener) {
		this.type = type;
		this.listener = listener;
	}

	public EventType getType() {
		return type;
	}

	public Object getListener() {
		return listener;
	}
}
