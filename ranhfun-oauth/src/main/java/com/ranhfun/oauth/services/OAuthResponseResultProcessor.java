package com.ranhfun.oauth.services;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Response;

import com.ranhfun.oauth.OAuthResponse;

public class OAuthResponseResultProcessor implements 
	ComponentEventResultProcessor<OAuthResponse> {

    private final Response response;

    public OAuthResponseResultProcessor(Response response)
    {
        this.response = response;
    }
	
	public void processResultValue(OAuthResponse oauthResponse) throws IOException {

        response.disableCompression();

        HttpServletResponse httpServletResponse = oauthResponse.getServletResponse();
        
        httpServletResponse.flushBuffer();
	}
	
}
