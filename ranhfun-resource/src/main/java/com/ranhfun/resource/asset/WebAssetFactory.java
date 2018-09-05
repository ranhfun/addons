package com.ranhfun.resource.asset;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.internal.services.AbstractAsset;
import org.apache.tapestry5.internal.services.RequestConstants;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.assets.AssetPathConstructor;

public class WebAssetFactory implements AssetFactory {
	
    private final Resource rootResource;
    
    private final AssetPathConstructor assetPathConstructor;

    private final AssetPathConverter converter;

    private final boolean invariant;    
    
    private final boolean externalMode;
    
    private final String externalParam;
    
    private final String applicationVersion;
    
    public WebAssetFactory(String site, 
    		
    		AssetPathConstructor assetPathConstructor, Context context,

            AssetPathConverter converter, boolean externalMode,
    		String externalParam, String applicationVersion)
    {
        this.assetPathConstructor = assetPathConstructor;
        this.converter = converter;
        
        rootResource = new WebResource(context, site, "/", externalMode);
        invariant = this.converter.isInvariant();     
        this.externalMode = externalMode;
        this.externalParam = externalParam;
        this.applicationVersion = applicationVersion;
    }

    public Asset createAsset(final Resource resource)
    {
    	if (!(resource instanceof WebResource)) {
			throw new IllegalArgumentException("illegal web resource:" + resource);
		}
    	final WebResource webResource = (WebResource)resource;
    	if (!externalMode  || !webResource.getSite().startsWith("http://")) {
            String defaultPath = assetPathConstructor.constructAssetPath(RequestConstants.CONTEXT_FOLDER, webResource.getSite() + "/" + resource.getPath());

            if (invariant)
            {
                return createInvariantAsset(resource, defaultPath);
            }

            return createVariantAsset(resource, defaultPath);
		}
        return new AbstractAsset(true)
        {
            public Resource getResource()
            {
                return webResource;
            }

            public String toClientURL()
            {
            	StringBuffer path = new StringBuffer(webResource.getPath());
            	if (path.indexOf("?")>-1) {
            		path.append("&");
				} else {
					path.append("?");
				}
            	path.append(externalParam).append("=").append(applicationVersion);
            	return webResource.getSite() + "/" + path.toString();
            }
        };
    }
    
    private Asset createInvariantAsset(final Resource resource, final String defaultPath)
    {
        return new AbstractAsset(true)
        {
            private String clientURL;

            public Resource getResource()
            {
                return resource;
            }

            public synchronized String toClientURL()
            {
                if (clientURL == null)
                {
                    clientURL = converter.convertAssetPath(defaultPath);
                }

                return clientURL;
            }
        };
    }

    private Asset createVariantAsset(final Resource resource, final String defaultPath)
    {
        return new AbstractAsset(false)
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
