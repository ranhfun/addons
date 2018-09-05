package com.ranhfun.vaadin.services;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

import com.ranhfun.vaadin.VaadinSymbols;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;

public class VaadinRequestFilter implements HttpServletRequestFilter {

	private String filterPath;
	private VaadinServlet vaadinServlet;
	private PerthreadManager perthreadManager;
	
	public VaadinRequestFilter(@Symbol(VaadinSymbols.MAPPING_PREFIX) String filterPath,
			final UIProvider uiProvider,
			RegistryShutdownHub hub, PerthreadManager perthreadManager,
			final ApplicationGlobals applicationGlobals) throws ServletException {
		this.filterPath = "(" + filterPath + "/.*)|(/VAADIN/.*)";
		this.perthreadManager = perthreadManager;
		this.vaadinServlet = new VaadinServlet() {
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void servletInitialized() throws ServletException {
			
		        getService().addSessionInitListener(new SessionInitListener() {
		            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public void sessionInit(SessionInitEvent event)
		                    throws ServiceException {
		                event.getSession().addUIProvider(uiProvider);
		            }
		        });
			 }
		};
		vaadinServlet.init(new ServletConfig() {
			public String getServletName() { return vaadinServlet.getClass().getName(); }
			public ServletContext getServletContext() {
				return applicationGlobals.getServletContext();
			}
			public String getInitParameter(String pArg0) {
				return null;
			}
		
			public Enumeration getInitParameterNames() {
				return new Enumeration(){
					public boolean hasMoreElements() { return false; }
					public Object nextElement() {
						throw new NoSuchElementException();
					}
				};
			}
		});
		hub.addRegistryShutdownListener(new Runnable() {
			public void run() {
				vaadinServlet.destroy();
			}
		});
	}
	
	public boolean service(HttpServletRequest request, HttpServletResponse response,
			HttpServletRequestHandler handler) throws IOException {
		final String path = request.getServletPath();
		Pattern p = Pattern.compile(filterPath, Pattern.CASE_INSENSITIVE);
		final Matcher m = p.matcher(path);   
		if (m.matches()) {
			try {
				vaadinServlet.service(new HttpServletRequestWrapper(request) {
					public String getPathInfo() {
						return path.indexOf("/", 2)>1?path.substring(path.indexOf("/", 2)):super.getPathInfo();
					}
				}, response);
			} catch (Exception e) {
				throw new RuntimeException("Vaadin VaadinServlet fail ===================> " + path, e);
			} finally {
				perthreadManager.cleanup();
			}
			return true;
		}
		return handler.service(request, response);
	}
}
