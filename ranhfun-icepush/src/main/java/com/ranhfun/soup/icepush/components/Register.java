package com.ranhfun.soup.icepush.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

import com.ranhfun.soup.icepush.base.IcepushBase;

public class Register extends IcepushBase {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String callback;
	
	boolean beginRender(MarkupWriter w) {
		super.beginRender();
		// Write script to register;
		w.writeRaw("<script type=\"text/javascript\">");
		w.writeRaw("ice.push.register(['" + getPushid() + "']," + callback + ");");
		w.writeRaw("</script>");
		return false;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String cb) {
		this.callback = cb;
	}
	
}
