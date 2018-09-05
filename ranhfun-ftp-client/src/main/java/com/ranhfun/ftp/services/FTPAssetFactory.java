package com.ranhfun.ftp.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.internal.services.AbstractAsset;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.assets.AssetPathConstructor;

import com.ranhfun.ftp.FTPAssetConstants;

public class FTPAssetFactory implements AssetFactory {

    private final AssetPathConstructor assetPathConstructor;

    private final Resource rootResource;

    private final AssetPathConverter converter;

    private final boolean invariant;

    public FTPAssetFactory(AssetPathConstructor assetPathConstructor, Context context,

    AssetPathConverter converter, @Symbol(FTPAssetConstants.FTP_SITE) String site)
    {
        this.assetPathConstructor = assetPathConstructor;
        this.converter = converter;

        rootResource = new FTPResource(site, "/");
        invariant = this.converter.isInvariant();
    }

    public Asset createAsset(final Resource resource)
    {
    	if (!(resource instanceof FTPResource)) {
			throw new IllegalArgumentException("illegal ftp resource:" + resource);
		}
    	final FTPResource ftpResource = (FTPResource)resource;
        return new AbstractAsset(invariant)
        {
            public Resource getResource()
            {
                return resource;
            }

            public String toClientURL()
            {
            	return ftpResource.getSite() + "/" + ftpResource.getPath();
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
