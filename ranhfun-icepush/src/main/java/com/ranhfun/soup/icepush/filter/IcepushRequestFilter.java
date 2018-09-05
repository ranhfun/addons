package com.ranhfun.soup.icepush.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.icepush.PushContext;
import org.icepush.servlet.MainServlet;

import com.ranhfun.soup.icepush.services.PushContextHolder;

public class IcepushRequestFilter implements HttpServletRequestFilter {

	private MainServlet pseudoServlet;
	
	private PushContextHolder pushContextHolder;
	
	private ApplicationGlobals applicationGlobals;
	
	private PerthreadManager perthreadManager;
	
	public IcepushRequestFilter(ApplicationGlobals applicationGlobals, PushContextHolder pushContextHolder, RegistryShutdownHub hub, PerthreadManager perthreadManager) {
		this.applicationGlobals = applicationGlobals;
		this.pushContextHolder = pushContextHolder;
		pseudoServlet = new MainServlet(applicationGlobals.getServletContext());
		this.perthreadManager = perthreadManager;
		hub.addRegistryShutdownListener(new Runnable() {
			public void run() {
				pseudoServlet.shutdown();
			}
		});
	}
	
	public boolean service(HttpServletRequest request,
			HttpServletResponse response, HttpServletRequestHandler handler)
			throws IOException {
		String path = request.getServletPath();
		String pathInfo = request.getPathInfo();
		pushContextHolder.set(PushContext.getInstance(applicationGlobals.getServletContext()));
		if (pathInfo != null) path += pathInfo;
		Pattern p = Pattern.compile(".*\\.icepush", Pattern.CASE_INSENSITIVE);
		if (p.matcher(path).matches()) {
			try {
				pseudoServlet.service(request, response);
			} catch (Exception e) {
				throw new RuntimeException("Icepush PseudoServlet fail ===================> " + path);
			} finally {
				perthreadManager.cleanup();
			}
			return true;
		}
		
		return handler.service(request, response);
	}

}
