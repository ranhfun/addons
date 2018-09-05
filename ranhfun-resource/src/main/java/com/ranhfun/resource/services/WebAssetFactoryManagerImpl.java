package com.ranhfun.resource.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.assets.AssetPathConstructor;

import com.ranhfun.resource.ResourceConstants;
import com.ranhfun.resource.asset.WebAssetFactory;

public class WebAssetFactoryManagerImpl implements WebAssetFactoryManager {

	private final Map<String, String> configuration;
	private final Map<String, WebAssetFactory> factories;
	
    public WebAssetFactoryManagerImpl(
    		final Map<String, String> configuration,
    		AssetPathConstructor assetPathConstructor, 
    		Context context,
            AssetPathConverter converter, 
            @Symbol(ResourceConstants.EXTERNAL_MODE)
            boolean externalMode,
            @Symbol(ResourceConstants.EXTERNAL_PARAM)
            String externalParam,
            @Symbol(SymbolConstants.APPLICATION_VERSION)
            String applicationVersion) {
    	this.configuration = configuration;
        factories = new HashMap<String, WebAssetFactory>();
        for (Map.Entry<String, String> entry : configuration.entrySet()) {
			factories.put(entry.getKey(), new WebAssetFactory(entry.getValue(), assetPathConstructor, context, converter, externalMode, externalParam, applicationVersion));
		}
    }
	
	public String siteForAsset(String asset) {
		return configuration.get(asset);
	}

	public WebAssetFactory factoryForAsset(String asset) {
		return factories.get(asset);
	}

	public Map<String, String> sites() {
		return configuration;
	}
	
	public Map<String, WebAssetFactory> factories() {
		return factories;
	}

}
