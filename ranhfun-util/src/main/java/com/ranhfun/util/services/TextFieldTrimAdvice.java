package com.ranhfun.util.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;

public class TextFieldTrimAdvice implements MethodAdvice {

	public void advise(MethodInvocation invocation) {
		invocation.proceed();
		Object result = invocation.getReturnValue();
		if (result!=null && result instanceof String) {
			invocation.setReturnValue(StringUtils.trim((String)result));
		}
	}

}
