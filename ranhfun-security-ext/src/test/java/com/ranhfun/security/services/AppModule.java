package com.ranhfun.security.services;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.tynamo.shiro.extension.realm.text.ExtendedPropertiesRealm;

import com.ranhfun.security.Ext;
import com.ranhfun.security.services.ExtSecurityListener;
import com.ranhfun.security.services.ExtSecurityManager;
import com.ranhfun.security.services.SecurityExtModule;

@SubModule(SecurityExtModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	    configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
    }

    @Contribute(WebSecurityManager.class)
    @Ext
	public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
		ExtendedPropertiesRealm realm = new ExtendedPropertiesRealm("classpath:shiro-users.properties");
		configuration.add(realm);
	}
    
	@Contribute(ExtSecurityManager.class)
	public static void setupExtSecurityManager(OrderedConfiguration<ExtSecurityListener> configuration) {
		configuration.add("extListener", new ExtSecurityListener() {
			
			public void performWebSecurityManager(
					DefaultWebSecurityManager webSecurityManager) {
				webSecurityManager.setCacheManager(new MemoryConstrainedCacheManager());
			}
		});
	}
	
}
