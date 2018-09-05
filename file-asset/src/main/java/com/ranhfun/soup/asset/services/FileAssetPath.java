package com.ranhfun.soup.asset.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

import com.ranhfun.soup.asset.FileAssetConstants;

public class FileAssetPath {

	private final Request request;
	
	private final HttpServletRequest httpServletRequest;
	
	private final AssetFactory assetFactory;
	
	private final BaseURLSource baseURLSource;
	
	private final String filePlacePath;
	
	public FileAssetPath(@Inject RequestGlobals requestGlobals,
				@Inject	@FileProvider AssetFactory assetFactory,
				@Inject BaseURLSource baseURLSource,
				@Inject @Symbol(FileAssetConstants.FILE_ASSET_FULL_PLACE) String filePlacePath) {
		request = requestGlobals.getRequest();
		httpServletRequest = requestGlobals.getHTTPServletRequest();
		this.assetFactory = assetFactory;
		this.baseURLSource = baseURLSource;
		this.filePlacePath = filePlacePath;
	}
	
	public String getPath(String relativePath) {
		StringBuffer path = new StringBuffer();
		path.append(baseURLSource.getBaseURL(request.isSecure()));
		path.append(getAsset(relativePath).toClientURL());
		return path.toString();
	}
	
	public Asset getAsset(String relativePath) {
		return assetFactory.createAsset(new FileResource(filePlacePath, relativePath));
	}
	
}
