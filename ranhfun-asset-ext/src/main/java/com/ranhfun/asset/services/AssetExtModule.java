package com.ranhfun.asset.services;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.services.Dispatcher;

public class AssetExtModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(ContextAssetDispatherAdvisor.class, ContextAssetDispatherAdvisorImpl.class);
	}

	@Advise(serviceInterface=Dispatcher.class, id="AssetExt")
    public static void assetExtAdvise(final ContextAssetDispatherAdvisor advisor,
            final MethodAdviceReceiver receiver)
    {
        advisor.addDispatchAdvice(receiver);
    }
}
