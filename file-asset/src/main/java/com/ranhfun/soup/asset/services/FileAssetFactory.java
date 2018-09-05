package com.ranhfun.soup.asset.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.internal.services.AbstractAsset;
import org.apache.tapestry5.internal.services.RequestConstants;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;

import com.ranhfun.soup.asset.FileAssetConstants;

public class FileAssetFactory implements AssetFactory {

    private final Request request;

    private final String pathPrefix;

    private final Resource rootResource;

    private final AssetPathConverter converter;

    private final boolean invariant;

    public FileAssetFactory(Request request, Context context,

                               String applicationVersion,

                               AssetPathConverter converter,
                               
                               String filePlacePath
    						   )
    {
        this.request = request;
        this.converter = converter;

        pathPrefix =/* RequestConstants.ASSET_PATH_PREFIX +*/ FileAssetConstants.FILE_ASSET_CLIENT_FOLDER
                + applicationVersion + "/";
        rootResource = new FileResource(filePlacePath, "");
        invariant = this.converter.isInvariant();
    }

    public Asset createAsset(final Resource resource)
    {
        final String defaultPath = request.getContextPath() + pathPrefix + resource.getPath();

        return new AbstractAsset(invariant)
        {
            public Resource getResource()
            {
                return resource;
            }

            public String toClientURL()
            {
                return converter.convertAssetPath(defaultPath);
            }
        };
    }

    /**
     * Returns the root {@link org.apache.tapestry5.internal.services.ContextResource}.
     */
    public Resource getRootResource()
    {
        return rootResource;
    }

}
