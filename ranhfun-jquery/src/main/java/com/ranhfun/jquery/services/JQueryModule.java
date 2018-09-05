package com.ranhfun.jquery.services;

import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.internal.services.javascript.CoreJavaScriptStack;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.ranhfun.jquery.JQueryConstants;
import com.ranhfun.jquery.services.javascript.FormFragmentSupportStack;
import com.ranhfun.jquery.services.javascript.FormSupportStack;
import com.ranhfun.jquery.services.javascript.JQueryJavaScriptStack;
import com.ranhfun.jquery.services.javascript.js.JSModule;

@SubModule(JSModule.class)
public class JQueryModule {
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(JQueryConstants.TAPESTRY_JQUERY_PATH, "classpath:com/ranhfun/jquery");
        configuration.add(JQueryConstants.JQUERY_ALIAS, "$");
        configuration.add(JQueryConstants.JQUERY_URL, "classpath:com/ranhfun/jquery/jquery-1.10.2.js");
        configuration.add(JQueryConstants.JQUERY_UI_URL, "classpath:com/ranhfun/jquery/jquery-ui.js");
        configuration.add(JQueryConstants.SUPPRESS_PROTOTYPE, "true");
    }
	
    public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration,
    		@Symbol(JQueryConstants.SUPPRESS_PROTOTYPE)
            boolean suppressPrototype)
    {
    	configuration.addInstance(JQueryConstants.PROTOTYPE_STACK, CoreJavaScriptStack.class);
    	configuration.overrideInstance(InternalConstants.CORE_STACK_NAME, JQueryJavaScriptStack.class);
    	if(suppressPrototype)
    	{
			configuration.addInstance(FormSupportStack.STACK_ID, FormSupportStack.class);
			configuration.addInstance(FormFragmentSupportStack.STACK_ID, FormFragmentSupportStack.class);
    	}
    }
    
    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("ranhfun-jquery", "com/ranhfun/jquery");
    }
	
    @Contribute(ComponentClassTransformWorker2.class)
    @Primary
    public static void addWorker(OrderedConfiguration<ComponentClassTransformWorker2> configuration,
    		@Symbol(JQueryConstants.SUPPRESS_PROTOTYPE) boolean suppressPrototype) {
    	if(suppressPrototype)
    	{
	    	configuration.addInstance("FormFragmentResourcesInclusionWorker", FormFragmentResourcesInclusionWorker.class, "after:RenderPhase");
			configuration.addInstance("FormResourcesInclusionWorker", FormResourcesInclusionWorker.class, "after:RenderPhase");
    	}
    }
    
    @Advise
    @Match("AssetPathConverter")
    public static void modifyJsfile(MethodAdviceReceiver receiver, final AssetSource source, 
    		@Symbol(JQueryConstants.SUPPRESS_PROTOTYPE) boolean prototype)
    	throws SecurityException, NoSuchMethodException{

    	MethodAdvice advise = new MethodAdvice() {

			public void advise(MethodInvocation invocation) {

				invocation.proceed();

				if(invocation.getReturnValue().toString().endsWith("ProgressiveDisplay.js")){
					invocation.setReturnValue( source.getExpandedAsset("${tapestry.jquery.path}/progressiveDisplay-jquery.js").toClientURL());
				}
				else if(invocation.getReturnValue().toString().endsWith("exceptiondisplay.js")){
					invocation.setReturnValue( source.getExpandedAsset("${tapestry.jquery.path}/exceptiondisplay-jquery.js").toClientURL());
				}
			}
		};
		
		if(prototype)
		receiver.adviseMethod(receiver.getInterface().getMethod("convertAssetPath", String.class),advise);
    }
    
}
