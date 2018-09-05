package com.ranhfun.oauth2.services;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Response;

public class OAuthResponseResultProcessor implements 
	ComponentEventResultProcessor<OAuthResponse> {

    private final Response response;

    public OAuthResponseResultProcessor(Response response)
    {
        this.response = response;
    }
	
	public void processResultValue(OAuthResponse oAuthResponse) throws IOException {
        if (oAuthResponse.getLocationUri()!=null) {
        	response.sendRedirect(oAuthResponse.getLocationUri());
		} else {
			response.setStatus(oAuthResponse.getResponseStatus());
			PrintWriter pw = response.getPrintWriter("text/html");
			pw.write(oAuthResponse.getBody());
			pw.flush();
			pw.close();
		}
	}

}
