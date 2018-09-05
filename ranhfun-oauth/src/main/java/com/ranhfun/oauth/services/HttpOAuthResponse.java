package com.ranhfun.oauth.services;

import javax.servlet.http.HttpServletResponse;

import com.ranhfun.oauth.OAuthResponse;

public class HttpOAuthResponse implements OAuthResponse {

	private final HttpServletResponse httpServletResponse;

	public HttpOAuthResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}
	
	public HttpServletResponse getServletResponse() {
		return httpServletResponse;
	}

}
