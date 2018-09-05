package com.ranhfun.jpa.services;

import java.util.EventListener;

import org.hibernate.Interceptor;

public interface InterceptorListener extends EventListener {

	void afterInteceptorInit(Interceptor interceptor);
	
}
