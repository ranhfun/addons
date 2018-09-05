package com.ranhfun.soup.editor.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.upload.services.UploadModule;

import com.ranhfun.soup.asset.services.FileAssetModule;
import com.ranhfun.soup.editor.EditorConstants;

@SubModule({FileAssetModule.class,UploadModule.class})
public class EditorModule
{
    public static void bind(ServiceBinder binder)
    {
    	//binder.bind(BaseURLSource.class, FileBaseURLSourceImpl.class).withId("BaseUrlSourceOverride");
    }
    
    public static void contributeFactoryDefaults(
            MappedConfiguration<String, String> configuration)
    {
        // file place
        //configuration.add(FileAssetConstants.FILE_ASSET_FULL_PLACE, "target/files");
        configuration.add(EditorConstants.KIND_EDITOR_FILE_TYPES, "gif,jpg,jpeg,png,bmp,zip,rar,txt,xml,xls,doc,pdf");
        configuration.add(EditorConstants.KIND_EDITOR_FILE_MAX_SIZE, "1000000");
    }
    
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("editor", "com.ranhfun.soup.editor"));
    }
}
