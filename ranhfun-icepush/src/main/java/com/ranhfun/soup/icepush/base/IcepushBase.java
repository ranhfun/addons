package com.ranhfun.soup.icepush.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.icepush.PushContext;
import org.icepush.notify.GroupNotifier;
import org.icepush.notify.Notifier;

public class IcepushBase {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String group;
	@Parameter(defaultPrefix = BindingConstants.PROP)
	private Notifier notifier;
	private String pushid;

	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private PushContext pushContext;
	
	@Inject
	private JavaScriptSupport renderSupport;
	
	@Inject
	private Request request2;
	
	public void beginRender() throws RuntimeException {

		renderSupport.importJavaScriptLibrary(request2.getContextPath() + "/code.icepush");
		
		// Get a push id;
		final PushContext pc = pushContext;
		if (pc == null) {
			throw (new RuntimeException(
					"PushContext not available in IcepushBase.beginRender()"));
		}
		pushid = pc.createPushId(request, response);

		// Find the notifier bean;
		if (notifier != null) {
			notifier.setPushContext(pc);
		}

		// Set group if there is one;
		if (group == null) {
			group = pushid;		
		}
		pc.addGroupMember(group, pushid);
		if (notifier != null) {
			try {
				// Set group in notifier;
				GroupNotifier gnotifier = (GroupNotifier) notifier;
				gnotifier.setGroup(group);
			} catch (ClassCastException e) {
			}
		}
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String grp) {
		this.group = grp;
	}

	public Notifier getNotifier() {
		return notifier;
	}

	public void setNotifier(Notifier notifier) {
		this.notifier = notifier;
	}

	public String getPushid() {
		return pushid;
	}

	public void setPushid(String pushid) {
		this.pushid = pushid;
	}
}
