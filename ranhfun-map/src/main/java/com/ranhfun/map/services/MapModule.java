package com.ranhfun.map.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.meta.MetaWorker;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.ranhfun.map.MapConstants;
import com.ranhfun.map.transform.ImportMapWorker;

public class MapModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(BaiduService.class, BaiduServiceImpl.class);
	}
	
	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void provideFactoryDefaults(
			final MappedConfiguration<String, String> configuration) {
		configuration.add(MapConstants.BAIDU_AK, "eMw169UAMbBBx4THHlSLAwK0");
		configuration.add(MapConstants.BAIDU_CLIENT_AK, "PsYVt8CFuAGGjykeV16l6xYI");
	}

    @Contribute(ComponentClassTransformWorker2.class)
    @Primary
    public static void provideTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration,
            MetaWorker metaWorker,
            ComponentClassResolver resolver)
    {
    	configuration.addInstance("ImportMap", ImportMapWorker.class);
    }
}
