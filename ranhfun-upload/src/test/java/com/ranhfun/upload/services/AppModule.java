package com.ranhfun.upload.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.upload.services.UploadModule;
import com.ranhfun.vfs.VFSAssetConstants;

@SubModule(UploadModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	       configuration.add(VFSAssetConstants.FILE_FULL_PLACE, "/target/files");
    }
	
}
