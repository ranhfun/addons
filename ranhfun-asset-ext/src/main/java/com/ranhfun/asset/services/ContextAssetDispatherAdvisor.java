package com.ranhfun.asset.services;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;

public interface ContextAssetDispatherAdvisor {

    void addDispatchAdvice(MethodAdviceReceiver receiver);
	
}
