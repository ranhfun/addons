package com.ranhfun.asset.services;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.internal.services.RequestConstants;
import org.apache.tapestry5.internal.services.ResourceStreamer;
import org.apache.tapestry5.internal.services.assets.ContextAssetRequestHandler;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

public class ContextDispatherMethodAdvice implements MethodAdvice {

	private final String pathPrefix;
	private final ContextAssetRequestHandler requestHandler;
	private final Pattern p;
	
	public ContextDispatherMethodAdvice(final AssetFactory contextAssetFactory,
			final ResourceStreamer streamer,
			final String applicationVersion, 
			final String applicationFolder,  final String assetPathPrefix) {
		this.requestHandler = new ContextAssetRequestHandler(streamer,  contextAssetFactory.getRootResource());
        String folder = applicationFolder.equals("") ? "" : "/" + applicationFolder;
        this.pathPrefix = folder + assetPathPrefix + applicationVersion + "/";
        p = Pattern.compile(folder + assetPathPrefix + ".*/" + RequestConstants.CONTEXT_FOLDER +  "/.*", Pattern.CASE_INSENSITIVE);
	}
	
	public void advise(MethodInvocation invocation) {
		invocation.proceed();
		if (invocation.getReturnValue().equals(false)) {
			Request request = (Request) invocation.getParameter(0);
			Response response = (Response) invocation.getParameter(1);
			String path = request.getPath();
			path = path.replaceAll("////", "/").trim();
			if (p.matcher(path).matches() && !path.startsWith(pathPrefix)) {
				 String extraPath = path.substring(path.indexOf("/" + RequestConstants.CONTEXT_FOLDER +  "/") + 5);
            	 try {
					 boolean handled = requestHandler.handleAssetRequest(request, response, extraPath);
		             if (!handled) {
							response.sendError(HttpServletResponse.SC_NOT_FOUND, path);
		             }
            	 } catch (IOException e) {
					 invocation.setCheckedException(e);
				 }
            	 invocation.setReturnValue(true);
			}
		}
	}
}
