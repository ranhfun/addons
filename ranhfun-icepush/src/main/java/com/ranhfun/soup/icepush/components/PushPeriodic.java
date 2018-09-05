package com.ranhfun.soup.icepush.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.ranhfun.soup.icepush.timer.GroupIntervalTimer;

public class PushPeriodic {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String group;
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private long interval;
	
	@Inject
	private GroupIntervalTimer timer;

	@Inject
	private JavaScriptSupport renderSupport;
	
	@Inject
	private Request request;
	
	boolean beginRender(MarkupWriter w) throws RuntimeException {

		renderSupport.importJavaScriptLibrary(request.getContextPath() + "/code.icepush");
		
		try {
			timer.addGroup(group, interval);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e.toString());
		}
		
		return false;
		
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String grp) {
		this.group = grp;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}
