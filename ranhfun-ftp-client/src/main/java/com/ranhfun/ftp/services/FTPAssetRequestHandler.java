package com.ranhfun.ftp.services;

import java.io.IOException;

import org.apache.tapestry5.internal.services.ResourceStreamer;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.assets.AssetRequestHandler;

public class FTPAssetRequestHandler  implements AssetRequestHandler {

    private final ResourceStreamer resourceStreamer;

    private final Resource rootFTPResource;

    public FTPAssetRequestHandler(ResourceStreamer resourceStreamer, Resource rootFTPResource)
    {
        this.resourceStreamer = resourceStreamer;
        this.rootFTPResource = rootFTPResource;
    }

    public boolean handleAssetRequest(Request request, Response response, String extraPath) throws IOException
    {
        Resource resource = rootFTPResource.forFile(extraPath);

        resourceStreamer.streamResource(resource);

        return true;
    }
	
}
