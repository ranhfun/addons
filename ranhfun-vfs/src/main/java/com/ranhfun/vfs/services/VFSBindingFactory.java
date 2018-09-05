package com.ranhfun.vfs.services;

import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AssetBinding;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BindingFactory;

public class VFSBindingFactory implements BindingFactory {

    private final AssetFactory assetFactory;
    private final FileSystemManager fs;
    private final String fileFullPlace;
    
    public VFSBindingFactory(AssetFactory assetFactory, FileSystemManager fs,String fileFullPlace)
    {
        this.assetFactory = assetFactory;
        this.fs = fs;
        this.fileFullPlace = fileFullPlace;
    }

    public Binding newBinding(String description, ComponentResources container, ComponentResources component,
                              String expression, Location location)
    {
        Asset asset = assetFactory.createAsset(new VFSResource(fs, fileFullPlace, expression));
        
        return new AssetBinding(location, description, asset);
    }
	
}
