package com.ranhfun.editor.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.ranhfun.editor.EditorConstants;

public class EditorModule {

	public static void bind(ServiceBinder binder)
	{
		binder.bind(EditorServices.class, EditorServicesImpl.class);
	}
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(EditorConstants.KIND_EDITOR_FILE_TYPES, "gif,jpg,jpeg,png,bmp,zip,rar,txt,xml,xls,doc,pdf");
        configuration.add(EditorConstants.KIND_EDITOR_FILE_MAX_SIZE, "1000000");
    }
	
    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker2> configuration)
    {
    	configuration.addInstance("ImportKindeditorWorker", ImportKindeditorWorker.class, "before:Import");
    }
    
    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("ranhfun-editor", "kindeditor");
    }
    
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("editor", "com.ranhfun.editor"));
    }
}
