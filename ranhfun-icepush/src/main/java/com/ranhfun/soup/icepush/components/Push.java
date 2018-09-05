package com.ranhfun.soup.icepush.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.icepush.PushContext;

public class Push {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String group;
	
	@Inject
	private JavaScriptSupport renderSupport;
	
	@Inject
	private Request request;
	
	@Inject
	private PushContext pushContext;
	
	boolean beginRender() throws RuntimeException {
		
		renderSupport.importJavaScriptLibrary(request.getContextPath() + "/code.icepush");
		
		pushContext.push(group);
		
		return false;

	}

	public void release() {
		group = null;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String grp) {
		this.group = grp;
	}
	
}
