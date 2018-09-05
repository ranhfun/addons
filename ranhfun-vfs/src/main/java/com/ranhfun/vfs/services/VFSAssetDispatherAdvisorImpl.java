package com.ranhfun.vfs.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.ResourceStreamer;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;

import com.ranhfun.vfs.VFSProvider;

public class VFSAssetDispatherAdvisorImpl implements VFSAssetDispatherAdvisor {

	private final AssetFactory conAssetFactory;
	private final ResourceStreamer streamer;
	private final String applicationVersion;
	private final String applicationFolder;
	private final String assetPathPrefix;
	
	public VFSAssetDispatherAdvisorImpl(
			@VFSProvider
    	    AssetFactory contextAssetFactory,
    	    ResourceStreamer streamer,
		    @Symbol(SymbolConstants.APPLICATION_VERSION)
		    String applicationVersion,
		
		    @Symbol(SymbolConstants.APPLICATION_FOLDER)
		    String applicationFolder,
		
		    @Symbol(SymbolConstants.ASSET_PATH_PREFIX)
		    String assetPathPrefix) {
		this.conAssetFactory = contextAssetFactory;
		this.streamer = streamer;
		this.applicationVersion = applicationVersion;
		this.applicationFolder = applicationFolder;
		this.assetPathPrefix = assetPathPrefix;
	}
	
	public void addDispatchAdvice(MethodAdviceReceiver receiver) {
        receiver.adviseAllMethods(new VFSDispatherMethodAdvice(conAssetFactory, streamer, applicationVersion, applicationFolder, assetPathPrefix));
	}

}
