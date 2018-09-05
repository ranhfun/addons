package com.ranhfun.oauth.pages.consumer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.ranhfun.oauth.services.HttpOAuthResponse;
import com.ranhfun.oauth.services.OAuthProvider;
import com.ranhfun.oauth.util.OAuthUtils;

public class Registration {

	@Inject
	private Logger logger;

	@Inject
	private HttpServletRequest req;
	
	@Inject
	private HttpServletResponse resp;
	
	@Inject
	private OAuthProvider provider;
	
	Object onActivate() throws IOException {
        logger.debug("Consumer registration");
        
        try{
            String[] values = req.getParameterValues(OAuth.OAUTH_CONSUMER_KEY);
            if (values == null || values.length != 1) {
                resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                return new HttpOAuthResponse(resp);
            }
            
            String consumerKey = URLDecoder.decode(values[0], "UTF-8");
            String[] scopes = req.getParameterValues("xoauth_scope");
            if (scopes != null) {
                provider.registerConsumerScopes(consumerKey, scopes);
            }
            
            String[] permissions = req.getParameterValues("xoauth_permission");
            if (permissions != null) {
                provider.registerConsumerPermissions(consumerKey, permissions);
            }
            
            resp.setStatus(HttpURLConnection.HTTP_OK);
            logger.debug("All OK");

        } catch (Exception x) {
            logger.error("Exception ", x);
            OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
        }
		return new HttpOAuthResponse(resp);
	}
	
}
