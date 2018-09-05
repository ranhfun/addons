package com.ranhfun.oauth.pages;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.ranhfun.oauth.OAuthException;
import com.ranhfun.oauth.OAuthToken;
import com.ranhfun.oauth.services.HttpOAuthResponse;
import com.ranhfun.oauth.services.OAuthProvider;
import com.ranhfun.oauth.services.OAuthValidator;
import com.ranhfun.oauth.util.OAuthUtils;

public class RequestToken {

	@Inject
	private Logger logger;

	@Inject
	private HttpServletRequest req;
	
	@Inject
	private HttpServletResponse resp;
	
	@Inject
	private OAuthProvider provider;
	
	@Inject
	private OAuthValidator validator;
	
	Object onActivate() throws IOException {
		logger.debug("Request token");
		OAuthMessage message = OAuthUtils.readMessage(req);
		try{
			// require some parameters
			message.requireParameters(OAuth.OAUTH_CONSUMER_KEY,
					OAuth.OAUTH_SIGNATURE_METHOD,
					OAuth.OAUTH_SIGNATURE,
					OAuth.OAUTH_TIMESTAMP,
					OAuth.OAUTH_NONCE);
			logger.debug("Parameters present");

			String consumerKey = message.getParameter(OAuth.OAUTH_CONSUMER_KEY);
			// load the OAuth Consumer
			com.ranhfun.oauth.OAuthConsumer consumer = provider.getConsumer(consumerKey);
			
			// create some structures for net.oauth
			OAuthConsumer _consumer = new OAuthConsumer(null, consumerKey, consumer.getSecret(), null);
			OAuthAccessor accessor = new OAuthAccessor(_consumer);
			
			// validate the message
			validator.validateMessage(message, accessor, null);

			// create a new Request Token
			String callbackURI = message.getParameter(OAuth.OAUTH_CALLBACK);
			if (callbackURI != null && consumer.getConnectURI() != null
			        && !callbackURI.startsWith(consumer.getConnectURI())) {
			    throw new OAuthException(400, "Wrong callback URI");
			}
			OAuthToken token = provider.makeRequestToken(consumerKey, 
			                            callbackURI, 
			                            req.getParameterValues("xoauth_scope"),
			                            req.getParameterValues("xoauth_permission"));

			// send the Token information to the Client
			OAuthUtils.sendValues(resp, OAuth.OAUTH_TOKEN, token.getToken(),OAuth.OAUTH_TOKEN_SECRET, token.getSecret(), OAuthUtils.OAUTH_CALLBACK_CONFIRMED_PARAM, "true");
			resp.setStatus(HttpURLConnection.HTTP_OK);
			logger.debug("All OK");

		} catch (OAuthException x) {
			OAuthUtils.makeErrorResponse(resp, x.getMessage(), x.getHttpCode(), provider);
		} catch (OAuthProblemException x) {
			OAuthUtils.makeErrorResponse(resp, x.getProblem(), OAuthUtils.getHttpCode(x), provider);
		} catch (Exception x) {
			logger.error("Exception ", x);
			OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
		} 
		return new HttpOAuthResponse(resp);
	}
	
}
