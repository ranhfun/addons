package com.ranhfun.upload.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;

import com.ranhfun.jquery.services.JQueryModule;
import com.ranhfun.vfs.services.VFSModule;

@SubModule({JQueryModule.class,VFSModule.class})
public class UploadModule {

    public static void bind(ServiceBinder binder)
    {
      binder.bind(AjaxUploadDecoder.class, AjaxUploadDecoderImpl.class);
    }
	
    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("ranhfun-upload", "upload");
    }
	
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("upload", "com.ranhfun.upload"));
    }
    
    public static void contributeHttpServletRequestHandler(final OrderedConfiguration<HttpServletRequestFilter> configuration,
            final AjaxUploadDecoder ajaxUploadDecoder) {

    		configuration.add("AjaxUploadFilter", new AjaxUploadServletRequestFilter(ajaxUploadDecoder), "after:IgnoredPaths");
    }
	
}
