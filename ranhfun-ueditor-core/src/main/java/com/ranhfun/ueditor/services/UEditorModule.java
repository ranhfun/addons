package com.ranhfun.ueditor.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptStack;

import com.ranhfun.ueditor.UEditorConstants;
import com.ranhfun.ueditor.services.javascript.UEditorStack;
import com.ranhfun.vfs.services.VFSModule;

@SubModule(VFSModule.class)
public class UEditorModule {

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("ranhfun-ueditor", "ueditor");
    }
    
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	 configuration.add(UEditorConstants.EXTERNAL_MODE, "true");
         configuration.add(UEditorConstants.UEDITOR_IMAGE_TYPES, "[.]jpg|png|jpeg|gif$");
         configuration.add(UEditorConstants.UEDITOR_IMAGE_MAX_SIZE, "1000000");
         configuration.add(UEditorConstants.UEDITOR_FILE_TYPES, "[.]rar|doc|docx|zip|pdf|txt|swf|wmv$");
         configuration.add(UEditorConstants.UEDITOR_FILE_MAX_SIZE, "1000000");
    }
    
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("ueditor", "com.ranhfun.ueditor"));
    }
    
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration)
    {
    	configuration.addInstance(UEditorStack.STACK_ID, UEditorStack.class);
    }
	
}
