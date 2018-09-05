package org.example.soup.icepush.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.icepush.PushContext;

public class Index {
	
	@Inject
	private ApplicationGlobals applicationGlobals;
	
	@Inject
	private PushContext pushContext;
	
	public void onActionFromPush() {
		pushContext.push("application");
		//pushRequestContext.getPushContext().push("application");	
	}
	
}
