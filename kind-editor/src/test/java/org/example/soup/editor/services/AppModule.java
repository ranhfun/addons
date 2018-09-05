package org.example.soup.editor.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.BaseURLSource;

import com.ranhfun.soup.asset.FileAssetConstants;
import com.ranhfun.soup.editor.EditorConstants;
import com.ranhfun.soup.editor.services.EditorModule;

@SubModule(EditorModule.class)
public class AppModule {

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "0.0.1");
        configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
        configuration.add(FileAssetConstants.FILE_ASSET_FULL_PLACE, "/target/files/");
        configuration.add(EditorConstants.KIND_EDITOR_FILE_TYPES, "gif,jpg,jpeg,png,bmp,xls");
    }
}
