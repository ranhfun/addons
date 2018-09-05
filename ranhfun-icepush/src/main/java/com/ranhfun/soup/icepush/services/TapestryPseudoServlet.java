package com.ranhfun.soup.icepush.services;

import org.apache.tapestry5.services.ApplicationGlobals;
import org.icepush.servlet.MainServlet;

public class TapestryPseudoServlet extends MainServlet {

	public TapestryPseudoServlet(ApplicationGlobals applicationGlobals) {
		super(applicationGlobals.getServletContext());
	}

}
