package com.ranhfun.soup.icepush.components;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;

import com.ranhfun.soup.icepush.base.IcepushBase;

public class Region extends IcepushBase {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String page;
	@Parameter(value="true", defaultPrefix = BindingConstants.LITERAL)
	private boolean evalJS;
	
	@Inject
	private ComponentResources componentResources;
	
	@Inject
	private ApplicationGlobals applicationGlobals;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;

	boolean beginRender(MarkupWriter w) throws RuntimeException {
		
		String id = componentResources.getId();
		// Write script to register;
		if (id == null) {
			id = getPushid();
		}
		w.writeRaw("<script type=\"text/javascript\">");
		w.writeRaw("ice.push.register(['" + getPushid() + "'], function(){\n");
		w.writeRaw("ice.push.get('"
				+ request.getContextPath() + page
				+ "', function(parameter) { \n parameter('group', '" + getGroup()
				+ "');} , ");
		w.writeRaw("function(statusCode, responseText) {\n");
		w.writeRaw("var container = document.getElementById('" + id + "');\n");
		w.writeRaw("container.innerHTML = responseText;");
		if( evalJS ){
			w.writeRaw("ice.push.searchAndEvaluateScripts(container);");
		}			
		w.writeRaw("});});");
		w.writeRaw("</script>");

		// Write the div;
		w.writeRaw("<div id=\"" + id + "\">");

		// Include the page;
		try {
			String params = new String("group=" + getGroup());
			applicationGlobals.getServletContext().getRequestDispatcher(
					page + ( page.indexOf("?") > -1 ? "&" : "?" ) + params).include(request,
					response);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ServletException se) {
			se.printStackTrace();
		}
		// Close the div;
		w.write("</div>");
		
		return false;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	public void setEvalJS(boolean eval){
		this.evalJS = eval;
	}
	
	public boolean getEvalJS(){
		return evalJS;
	}
	
}
