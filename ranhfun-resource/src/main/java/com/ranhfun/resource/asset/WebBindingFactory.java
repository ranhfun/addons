package com.ranhfun.resource.asset;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AssetBinding;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.Context;

public class WebBindingFactory implements BindingFactory {

    private final AssetFactory assetFactory;
    private final Context context;
    private final String site;
    private final boolean externalMode;
    
    public WebBindingFactory(AssetFactory assetFactory, Context context, String site, boolean externalMode)
    {
        this.assetFactory = assetFactory;
        this.context = context;
        this.site = site;
        this.externalMode = externalMode;
    }

    public Binding newBinding(String description, ComponentResources container, ComponentResources component,
                              String expression, Location location)
    {
        Asset asset = assetFactory.createAsset(new WebResource(context, site, expression, externalMode));
        
        return new AssetBinding(location, description, asset);
    }
	
}
