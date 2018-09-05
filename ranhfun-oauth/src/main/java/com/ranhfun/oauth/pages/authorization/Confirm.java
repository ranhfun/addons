package com.ranhfun.oauth.pages.authorization;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.ranhfun.oauth.OAuthRequestToken;
import com.ranhfun.oauth.services.HttpOAuthResponse;
import com.ranhfun.oauth.services.OAuthProvider;
import com.ranhfun.oauth.util.OAuthUtils;

public class Confirm {

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
            String[] values = req.getParameterValues(OAuth.OAUTH_TOKEN);
            if (values == null || values.length != 1) {
                resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                return new HttpOAuthResponse(resp);
            }
            String requestTokenKey = values[0];
            
            OAuthRequestToken requestToken = provider.getRequestToken(null, requestTokenKey);
            com.ranhfun.oauth.OAuthConsumer consumer = requestToken.getConsumer();
            
            values = req.getParameterValues("xoauth_end_user_decision");
            if (values == null || values.length != 1) {
                resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                return new HttpOAuthResponse(resp);
            }
            
            boolean authorized = "yes".equals(values[0]) || "true".equals(values[0]);
            
            String callback = requestToken.getCallback();
            if (authorized) 
            {
                String verifier = provider.authoriseRequestToken(consumer.getKey(), requestToken.getToken());
                
                if (callback == null) {
                    OAuthUtils.sendValues(resp, OAuth.OAUTH_TOKEN, requestTokenKey, OAuth.OAUTH_VERIFIER, verifier);
                    resp.setStatus(HttpURLConnection.HTTP_OK);
                } else {
                    List<OAuth.Parameter> parameters = new ArrayList<OAuth.Parameter>();
                    parameters.add(new OAuth.Parameter(OAuth.OAUTH_TOKEN, requestTokenKey));
                    parameters.add(new OAuth.Parameter(OAuth.OAUTH_VERIFIER, verifier));
                    String location = OAuth.addParameters(callback, parameters);
                    resp.addHeader("Location", location);
                    resp.setStatus(302);
                }
            } 
            else
            {
                // TODO : make sure this response is OAuth compliant 
                OAuthUtils.makeErrorResponse(resp, "Token has not been authorized", 503, provider);
            }
            
            logger.debug("All OK");

        } catch (Exception x) {
            logger.error("Exception ", x);
            OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
        }		
		return new HttpOAuthResponse(resp);
	}
	
}
