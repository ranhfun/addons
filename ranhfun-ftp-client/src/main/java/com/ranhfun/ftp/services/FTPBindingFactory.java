package com.ranhfun.ftp.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AssetBinding;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BindingFactory;

public class FTPBindingFactory implements BindingFactory {

    private final AssetFactory assetFactory;
    private final String site;
    
    public FTPBindingFactory(AssetFactory assetFactory, String site)
    {
        this.assetFactory = assetFactory;
        this.site = site;
    }

    public Binding newBinding(String description, ComponentResources container, ComponentResources component,
                              String expression, Location location)
    {
        Asset asset = assetFactory.createAsset(new FTPResource(site, expression));
        
        return new AssetBinding(location, description, asset);
    }
	
}
