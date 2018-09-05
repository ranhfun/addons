package com.ranhfun.soup.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.ranhfun.soup.transform.ScrollCleanWorker;

public class SoupModule {

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("ranhfun-soup", "com/ranhfun/soup");
    }
    
    
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("soup", "com.ranhfun.soup"));
    }
	
    @Contribute(ComponentClassTransformWorker2.class)
    @Primary
    public static void provideTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration,
            ComponentClassResolver resolver)
    {
    	configuration.addInstance("ScrollClean", ScrollCleanWorker.class);
    }
}
