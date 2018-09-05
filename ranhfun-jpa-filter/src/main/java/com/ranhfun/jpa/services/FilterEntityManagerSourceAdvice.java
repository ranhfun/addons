package com.ranhfun.jpa.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.hibernate.Filter;
import org.hibernate.Session;

public class FilterEntityManagerSourceAdvice implements MethodAdvice {

	private final FilterManager filterManager;
	
	public FilterEntityManagerSourceAdvice(FilterManager filterManager) {
		this.filterManager = filterManager;
	}

	@SuppressWarnings("rawtypes")
	public void advise(MethodInvocation invocation) {
		invocation.proceed();
		EntityManager em = (EntityManager) invocation.getReturnValue();
		if (em == null) return;
		for (Map.Entry<String, List<Parameter>> filter : filterManager.getParameters().entrySet()) {
			Filter filter2 = em.unwrap(Session.class).enableFilter(filter.getKey());
			for (Parameter parameter : filter.getValue()) {
				if (parameter.getValue() instanceof Collection) {
					filter2.setParameterList(parameter.getName(), (Collection)parameter.getValue());
				} else if (parameter.getValue() instanceof Object[]) {
					filter2.setParameterList(parameter.getName(), (Object[])parameter.getValue());
				} else {
					filter2.setParameter(parameter.getName(), parameter.getValue());
				}
			}
		}
	}

}
