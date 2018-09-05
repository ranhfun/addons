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

public class AccessToken {

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
		logger.debug("Access token");
		OAuthMessage message = OAuthUtils.readMessage(req);
		try{
			// request some parameters
			message.requireParameters(OAuth.OAUTH_CONSUMER_KEY,
					OAuth.OAUTH_TOKEN,
					OAuth.OAUTH_SIGNATURE_METHOD,
					OAuth.OAUTH_SIGNATURE,
					OAuth.OAUTH_TIMESTAMP,
					OAuth.OAUTH_NONCE,
					OAuthUtils.OAUTH_VERIFIER_PARAM);

			logger.debug("Parameters present");
			
			// load some parameters
			String consumerKey = message.getParameter(OAuth.OAUTH_CONSUMER_KEY);
			String requestTokenString = message.getParameter(OAuth.OAUTH_TOKEN);
			String verifier = message.getParameter(OAuth.OAUTH_VERIFIER);
			
			// get the Request Token to exchange
			OAuthToken requestToken = provider.getRequestToken(consumerKey, requestTokenString);
			
			// build some structures for net.oauth
			OAuthConsumer consumer = new OAuthConsumer(null, consumerKey, requestToken.getConsumer().getSecret(), null);
			OAuthAccessor accessor = new OAuthAccessor(consumer);
			accessor.requestToken = requestTokenString;
			accessor.tokenSecret = requestToken.getSecret();

			// verify the message signature
			validator.validateMessage(message, accessor, requestToken);

			// exchange the Request Token
			OAuthToken tokens = provider.makeAccessToken(consumerKey, requestTokenString, verifier);

			// send the Access Token
			OAuthUtils.sendValues(resp, OAuth.OAUTH_TOKEN, tokens.getToken(),OAuth.OAUTH_TOKEN_SECRET, tokens.getSecret());
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
