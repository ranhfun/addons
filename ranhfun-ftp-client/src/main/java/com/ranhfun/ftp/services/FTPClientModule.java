package com.ranhfun.ftp.services;

import org.apache.tapestry5.internal.services.ResourceStreamer;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.assets.AssetPathConstructor;
import org.apache.tapestry5.services.assets.AssetRequestHandler;

import com.ranhfun.ftp.FTPAssetConstants;
import com.ranhfun.ftp.FTPProvider;

public class FTPClientModule {

	public static void bind(final ServiceBinder binder)
	{
		binder.bind(FTPClientService.class, FTPClientServiceImpl.class);
	}
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(FTPAssetConstants.FTP_SITE, "117.27.146.20");
    	configuration.add(FTPAssetConstants.FTP_PORT, "1104");
    	configuration.add(FTPAssetConstants.FTP_USER_NAME, "boc");
    	configuration.add(FTPAssetConstants.FTP_USER_PASSWORD, "zhboc1104");
    	configuration.add(FTPAssetConstants.FTP_DEFAULT_DIR, "ftp");
    }
	
    @Marker(FTPProvider.class)
    public AssetFactory buildFTPAssetFactory(ApplicationGlobals globals,

    	    AssetPathConstructor assetPathConstructor,

    	    AssetPathConverter converter,
    	
    	@Symbol(FTPAssetConstants.FTP_SITE)
    	String site)
    {
        return new FTPAssetFactory(assetPathConstructor, globals.getContext(), converter, site);
    }
    
    public void contributeAssetSource(MappedConfiguration<String, AssetFactory> configuration,
            						  @FTPProvider AssetFactory ftpAssetFactory)
	{
    	configuration.add(FTPAssetConstants.FTP_ASSET, ftpAssetFactory);
	}
    
	public void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
			@FTPProvider AssetFactory assetFactory,
			@Symbol(FTPAssetConstants.FTP_SITE)
	    	String site)
	{
	    configuration.add(FTPAssetConstants.FTP_ASSET, new FTPBindingFactory(assetFactory, site));
	}
    
    public static void contributeAssetDispatcher(MappedConfiguration<String, AssetRequestHandler> configuration,

    	    @FTPProvider
    	    AssetFactory contextAssetFactory,

    	    ResourceStreamer streamer)
    {
        configuration.add(FTPAssetConstants.FTP_ASSET_CLIENT_FOLDER,
                new FTPAssetRequestHandler(streamer, contextAssetFactory.getRootResource()));

    }
}
