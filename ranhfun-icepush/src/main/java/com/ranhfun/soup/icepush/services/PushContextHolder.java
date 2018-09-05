package com.ranhfun.soup.icepush.services;

import org.icepush.PushContext;

public interface PushContextHolder {

	void set(PushContext pushContext);
	
	PushContext get();
	
	boolean isSet();
	
}
