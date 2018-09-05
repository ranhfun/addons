package com.ranhfun.soup.asset.services;

import org.apache.tapestry5.*;
import org.apache.tapestry5.internal.services.AssetResourceLocator;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.Request;

import com.ranhfun.soup.asset.FileAssetConstants;

public class FileAssetModule
{
    public static void bind(ServiceBinder binder)
    {
    	//binder.bind(BaseURLSource.class, FileBaseURLSourceImpl.class).withId("BaseUrlSourceOverride");
    	binder.bind(AssetResourceLocator.class, FileAssetResourceLocatorImpl.class).withId("Override");
    	binder.bind(FileAssetPath.class);
    }
    
    public static void contributeFactoryDefaults(
            MappedConfiguration<String, String> configuration)
    {
        // file place
        configuration.add(FileAssetConstants.FILE_ASSET_FULL_PLACE, "/target/files");
    }
    
    @Marker(FileProvider.class)
    public AssetFactory buildFileAssetFactory(@Inject 
											 Request request,
											
											 ApplicationGlobals globals,

                                             @Inject @Symbol(SymbolConstants.APPLICATION_VERSION)
                                             String applicationVersion,

                                             AssetPathConverter converter,
                                             @Inject @Symbol(FileAssetConstants.FILE_ASSET_FULL_PLACE)
                                             String filePlacePath
    										 )
    {
        return new FileAssetFactory(request, globals.getContext(), applicationVersion, converter, filePlacePath);
    }
    
    public void contributeAssetSource(MappedConfiguration<String, AssetFactory> configuration,
            						  @FileProvider AssetFactory fileAssetFactory)
	{
    	configuration.add("file", fileAssetFactory);
	}
    
    public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration,
    		@Local AssetResourceLocator override)
    {
      configuration.add(AssetResourceLocator.class, override);
    }
    
/*    public void contributeAlias(Configuration<AliasContribution> configuration, @Local BaseURLSource baseURLSource)
    {
        configuration.add(AliasContribution.create(BaseURLSource.class, baseURLSource));
    }*/
}
