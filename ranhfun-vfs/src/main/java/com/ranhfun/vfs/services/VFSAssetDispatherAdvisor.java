package com.ranhfun.vfs.services;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;

public interface VFSAssetDispatherAdvisor {

    void addDispatchAdvice(MethodAdviceReceiver receiver);
	
}
