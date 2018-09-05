package com.ranhfun.soup.icepush.services;

import org.apache.tapestry5.ioc.services.PerThreadValue;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.icepush.PushContext;

public class PushContextHolderImpl implements PushContextHolder {

	private final PerThreadValue<PushContext> pushContextValue;
	
	public PushContextHolderImpl(PerthreadManager perthreadManager) {
		pushContextValue = perthreadManager.createValue();
	}
	
	public void set(PushContext pushContext) {
		assert pushContext != null;
		pushContextValue.set(pushContext);
	}

	public PushContext get() {
		return pushContextValue.get();
	}

	public boolean isSet() {
		return pushContextValue.exists() && pushContextValue.get()!=null;
	}

}
