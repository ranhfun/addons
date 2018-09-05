package com.ranhfun.vfs.services;

import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.internal.services.AbstractAsset;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.assets.AssetPathConstructor;

import com.ranhfun.vfs.VFSAssetConstants;

public class VFSAssetFactory implements AssetFactory {

    private final AssetPathConstructor assetPathConstructor;

    private final Resource rootResource;

    private final AssetPathConverter converter;

    private final boolean invariant;

    public VFSAssetFactory(AssetPathConstructor assetPathConstructor, Context context,

    AssetPathConverter converter, FileSystemManager fs, @Symbol(VFSAssetConstants.FILE_FULL_PLACE) String fileFullPlace)
    {
        this.assetPathConstructor = assetPathConstructor;
        this.converter = converter;

        rootResource = new VFSResource(fs, fileFullPlace, "/");
        invariant = this.converter.isInvariant();
    }

    public Asset createAsset(final Resource resource)
    {
        final String defaultPath = assetPathConstructor.constructAssetPath(VFSAssetConstants.VFS_ASSET_CLIENT_FOLDER, resource
                .getPath());

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
