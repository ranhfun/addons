package com.ranhfun.ueditor.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.jquery.JQueryConstants;
import com.ranhfun.jquery.services.JQueryModule;
import com.ranhfun.ueditor.UEditorConstants;
import com.ranhfun.ueditor.services.UEditorModule;
import com.ranhfun.vfs.VFSAssetConstants;

@SubModule({JQueryModule.class,UEditorModule.class})
public class AppModule {

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	       configuration.add(VFSAssetConstants.FILE_FULL_PLACE, "/target/files");
	       configuration.add(JQueryConstants.SUPPRESS_PROTOTYPE, "false");
	       configuration.add(UEditorConstants.EXTERNAL_MODE, "false");
	}
	
}
