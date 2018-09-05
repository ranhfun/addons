package com.ranhfun.oauth.filter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;

import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.slf4j.Logger;

import com.ranhfun.oauth.OAuthException;
import com.ranhfun.oauth.OAuthToken;
import com.ranhfun.oauth.services.OAuthProvider;
import com.ranhfun.oauth.services.OAuthProviderChecker;
import com.ranhfun.oauth.services.OAuthValidator;
import com.ranhfun.oauth.util.OAuthUtils;

public class OAuthFilter implements HttpServletRequestFilter {

	public static final String OAUTH_AUTH_METHOD = "OAuth";

	private Set<Pattern> patterns = new HashSet<Pattern>();
	
	private OAuthProvider provider;
	private OAuthValidator validator;
	private Logger logger; 
	
	public OAuthFilter(OAuthProvider provider, OAuthValidator validator, Logger logger) {
		this.provider = new OAuthProviderChecker(provider);
		this.validator = validator;
		this.logger = logger;
	}

	public boolean service(HttpServletRequest request,
			HttpServletResponse response, final HttpServletRequestHandler handler)
			throws IOException {
		String path = request.getServletPath();
		String pathInfo = request.getPathInfo();

		if (pathInfo != null) path += pathInfo;

		final boolean[] res = new boolean[]{true};
		for (Pattern p : patterns) {
			if (p.matcher(path).matches()) {
				try {
					doFilter(request, response, new FilterChain()
					{
						public void doFilter(final ServletRequest request,
						                     final ServletResponse response) throws IOException, ServletException
						{
							res[0] = handler.service((HttpServletRequest) request, (HttpServletResponse) response);
						}
					});
				}  catch (ServletException e)
				{
					IOException ex = new IOException(e.getMessage());
					ex.initCause(e);
					throw ex;
				}
				return res[0];
			}
		}
		return handler.service(request, response);
	}
	

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		_doFilter((HttpServletRequest)request, (HttpServletResponse)response, filterChain);
	}
	
	protected void _doFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
	    
	    logger.debug("Filtering " + request.getMethod() + " " + request.getRequestURL().toString());

		OAuthMessage message = OAuthUtils.readMessage(request);
        try{

            message.requireParameters(OAuth.OAUTH_CONSUMER_KEY,
                    OAuth.OAUTH_SIGNATURE_METHOD,
                    OAuth.OAUTH_SIGNATURE,
                    OAuth.OAUTH_TIMESTAMP,
                    OAuth.OAUTH_NONCE);

            String consumerKey = message.getParameter(OAuth.OAUTH_CONSUMER_KEY);
            com.ranhfun.oauth.OAuthConsumer consumer = provider.getConsumer(consumerKey);
        
            OAuthToken accessToken = null;
            String accessTokenString = message.getParameter(OAuth.OAUTH_TOKEN);
            
            if (accessTokenString != null) { 
                accessToken = provider.getAccessToken(consumer.getKey(), accessTokenString);
                OAuthUtils.validateRequestWithAccessToken(
                        request, message, accessToken, validator, consumer);
            } else {
                OAuthUtils.validateRequestWithoutAccessToken(
                        request, message, validator, consumer);
            }
            
            request = createSecurityContext(request, consumer, accessToken);
            
            // let the request through with the new credentials
            logger.debug("doFilter");
            filterChain.doFilter(request, response);
            
        } catch (OAuthException x) {
            OAuthUtils.makeErrorResponse(response, x.getMessage(), x.getHttpCode(), provider);
        } catch (OAuthProblemException x) {
            OAuthUtils.makeErrorResponse(response, x.getProblem(), OAuthUtils.getHttpCode(x), provider);
        } catch (Exception x) {
            OAuthUtils.makeErrorResponse(response, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
        }
		
	}

	protected OAuthProvider getProvider() {
	    return provider;
	}
	
	
	protected HttpServletRequest createSecurityContext(HttpServletRequest request, 
	                                                   com.ranhfun.oauth.OAuthConsumer consumer,
	                                                   OAuthToken accessToken) 
	{
	    return request;
	}
}
