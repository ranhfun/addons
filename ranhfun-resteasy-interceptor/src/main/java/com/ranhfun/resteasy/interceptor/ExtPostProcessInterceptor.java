package com.ranhfun.resteasy.interceptor;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.util.HttpHeaderNames;

@Provider
public class ExtPostProcessInterceptor implements PostProcessInterceptor {

	public void postProcess(ServerResponse response) {
		System.out.println("HERE!!!!!");
		System.out.println(response.getMetadata().getFirst(HttpHeaderNames.CONTENT_TYPE));
		//response.getMetadata().putSingle(HttpHeaderNames.CONTENT_TYPE, MediaType.APPLICATION_XML_TYPE);
        response.getMetadata().add("custom-header", "hello");
	}

}
