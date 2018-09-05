package com.ranhfun.resource.services;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;

import com.ranhfun.resource.asset.WebAssetFactory;

@UsesMappedConfiguration(String.class)
public interface WebAssetFactoryManager {

	String siteForAsset(String asset);
	
	WebAssetFactory factoryForAsset(String asset);
	
	Map<String, String> sites();	
	
	Map<String, WebAssetFactory> factories();
}
