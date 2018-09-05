package com.ranhfun.soup.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.javascript.JavaScriptStack;

import com.ranhfun.jquery.services.JQueryModule;
import com.ranhfun.resource.services.ResourceModule;
import com.ranhfun.resource.services.WebAssetFactoryManager;
import com.ranhfun.soup.CommonConstants;
import com.ranhfun.soup.services.SoupModule;
import com.ranhfun.soup.services.javascript.AjaxUploadStack;
import com.ranhfun.soup.services.javascript.CommonJavaScriptStack;

@org.apache.tapestry5.ioc.annotations.SubModule({SoupModule.class,JQueryModule.class, ResourceModule.class})
public class SubModule {

	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void provideFactoryDefaults(
			final MappedConfiguration<String, String> configuration) {
		configuration.add(CommonConstants.COMMON_ASSET_SITE, "http://192.168.1.122/jdc");
	}
    
    @Contribute(WebAssetFactoryManager.class)
    public static void provideWebAssetFactoryManager(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(CommonConstants.COMMON_ASSET_SITE, "http://192.168.1.122/jdc");
    }
    
	
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration)
    {
    	configuration.addInstance(AjaxUploadStack.STACK_ID, AjaxUploadStack.class);
    	//configuration.addInstance(CommonJavaScriptStack.STACK_ID, CommonJavaScriptStack.class);
    }
	
}
