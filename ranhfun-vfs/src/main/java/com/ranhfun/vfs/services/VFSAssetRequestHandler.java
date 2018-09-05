package com.ranhfun.vfs.services;

import java.io.IOException;

import org.apache.tapestry5.internal.services.ResourceStreamer;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.assets.AssetRequestHandler;

public class VFSAssetRequestHandler  implements AssetRequestHandler {

    private final ResourceStreamer resourceStreamer;

    private final Resource rootVFSResource;

    public VFSAssetRequestHandler(ResourceStreamer resourceStreamer, Resource rootVFSResource)
    {
        this.resourceStreamer = resourceStreamer;
        this.rootVFSResource = rootVFSResource;
    }

    public boolean handleAssetRequest(Request request, Response response, String extraPath) throws IOException
    {
        Resource resource = rootVFSResource.forFile(extraPath);

        resourceStreamer.streamResource(resource);

        return true;
    }
	
}
