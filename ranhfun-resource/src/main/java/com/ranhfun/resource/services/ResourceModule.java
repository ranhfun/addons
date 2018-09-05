package com.ranhfun.resource.services;

import java.util.Map;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.meta.MetaWorker;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.ranhfun.resource.ResourceConstants;
import com.ranhfun.resource.asset.WebAssetFactory;
import com.ranhfun.resource.asset.WebBindingFactory;
import com.ranhfun.resource.transform.ImportWebAssetWorker;

public class ResourceModule {

	public static void bind(ServiceBinder binder)
    {
		binder.bind(WebAssetFactoryManager.class, WebAssetFactoryManagerImpl.class);
    }
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	 configuration.add(ResourceConstants.EXTERNAL_MODE, "true");
    	 configuration.add(ResourceConstants.EXTERNAL_PARAM, "v");
    }
	
    public void contributeAssetSource(MappedConfiguration<String, AssetFactory> configuration,
			  WebAssetFactoryManager manager)
	{
    	for (Map.Entry<String, WebAssetFactory> entry : manager.factories().entrySet()) {
    		configuration.add(entry.getKey(), entry.getValue());
		}
	}
	
	public void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
		WebAssetFactoryManager manager,	
		AssetSource assetSource,
		Context context,
		@Symbol(ResourceConstants.EXTERNAL_MODE)
		boolean externalMode)
	{
    	for (Map.Entry<String, WebAssetFactory> entry : manager.factories().entrySet()) {
    		configuration.add(entry.getKey(), new WebBindingFactory(entry.getValue(), context, manager.siteForAsset(entry.getKey()), externalMode));
		}
	}
	
    @Contribute(ComponentClassTransformWorker2.class)
    @Primary
    public static void provideTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration,
            MetaWorker metaWorker,
            ComponentClassResolver resolver)
    {
    	configuration.overrideInstance("Import", ImportWebAssetWorker.class, "after:RenderPhase");
    }
	
}
