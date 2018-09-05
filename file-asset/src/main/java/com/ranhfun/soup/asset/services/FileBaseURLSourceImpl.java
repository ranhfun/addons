package com.ranhfun.soup.asset.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

public class FileBaseURLSourceImpl implements BaseURLSource {

    private final Request request;

    public FileBaseURLSourceImpl(RequestGlobals requestGlobals)
    {
        this.request = requestGlobals.getRequest();
    }

    public String getBaseURL(boolean secure)
    {
        String protocol = secure ? "https" : "http";
        //int port = request.getServerPort();
        
        //if ((!secure && port==80)||(secure && port==8443)) {
        	return String.format("%s://%s", protocol, request.getServerName());
		//}
        //return String.format("%s://%s:%d", protocol, request.getServerName(), request.getServerPort());
    }

}
