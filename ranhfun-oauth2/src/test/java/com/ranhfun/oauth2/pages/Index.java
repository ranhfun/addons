package com.ranhfun.oauth2.pages;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

public class Index {
	
	Object onActivate() throws OAuthSystemException {
		OAuthProblemException ex = OAuthProblemException
	            .error(OAuthError.CodeResponse.ACCESS_DENIED, "Access denied")
	            .setParameter("testparameter", "testparameter_value")
	            .scope("album")
	            .uri("http://www.example.com/error");
		return OAuthResponse.errorResponse(400).error(ex).location("http://www.baidu.com").buildQueryMessage();
	}
	
}
