package com.ranhfun.jpa.services;

import java.util.Collections;

import javax.persistence.EntityManager;

import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.jpa.EntityManagerSource;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.hibernate.ejb.AvailableSettings;
import org.hibernate.engine.spi.SessionImplementor;

import com.ranhfun.jpa.InterceptorConstants;

public class InterceptorEntityManagerSourceAdvice implements MethodAdvice {

	private final InterceptorManager interceptorManager;
	private final String interceptorClass;
	
	public InterceptorEntityManagerSourceAdvice(InterceptorManager interceptorManager,
			@Symbol(InterceptorConstants.INTERCEPTOR_CLASS)
			String interceptorClass) {
		this.interceptorManager = interceptorManager;
		this.interceptorClass = interceptorClass;
	}

	public void advise(MethodInvocation invocation) {
		EntityManagerSource source = (EntityManagerSource)invocation.getInstance();
		String persistenceUnitName = (String)invocation.getParameter(0);
		EntityManager entityManager = source.getEntityManagerFactory(persistenceUnitName).createEntityManager(Collections.singletonMap(AvailableSettings.SESSION_INTERCEPTOR, interceptorClass));
		if (entityManager == null) return;
		interceptorManager.run(entityManager.unwrap(SessionImplementor.class).getInterceptor());
		invocation.setReturnValue(entityManager);
	}

}
