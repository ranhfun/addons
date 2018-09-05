package com.ranhfun.resource.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.asset.services.AssetExtModule;
import com.ranhfun.resource.ResourceConstants;
import com.ranhfun.resource.services.ResourceModule;
import com.ranhfun.resource.services.WebAssetFactoryManager;
import com.ranhfun.vfs.VFSAssetConstants;
import com.ranhfun.vfs.services.VFSModule;

@SubModule({ResourceModule.class, VFSModule.class, AssetExtModule.class})
public class AppModule {

    @Contribute(WebAssetFactoryManager.class)
    public static void provideWebAssetFactoryManager(
            final MappedConfiguration<String, String> configuration)
    {
        //configuration.add("front", "http://photo.chelian.me/jdc");
    	configuration.add("front", "front");
    }
    
    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(ResourceConstants.EXTERNAL_MODE, "false");
	    configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	    configuration.add(VFSAssetConstants.FILE_FULL_PLACE, "/target/files");
    }

}
