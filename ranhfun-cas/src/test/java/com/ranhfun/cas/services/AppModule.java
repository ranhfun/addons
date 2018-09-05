package com.ranhfun.cas.services;

import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.impl.SecurityFilterChain;

import com.ranhfun.cas.CasRealm;
import com.ranhfun.cas.CasSymbols;
import com.ranhfun.cas.services.CASModule;
import com.ranhfun.cas.services.CasSecurityFilterChainFactory;

@SubModule(CASModule.class)
public class AppModule {

	public static void contributeWebSecurityManager(Configuration<Realm> configuration, 
			@Inject @Symbol(CasSymbols.CAS_SERVER_URL_PREFIX)
			String casServerUrlPrefix,
			@Inject @Symbol(CasSymbols.CAS_SERVICE)
			String casService) {
		CasRealm realm = new CasRealm();
		realm.setCasServerUrlPrefix(casServerUrlPrefix);
		realm.setCasService(casService);
		configuration.add(realm);
	}
	
    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideApplicationDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(SecuritySymbols.LOGIN_URL, "/login");
    	configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    	configuration.add(CasSymbols.CAS_SERVER_URL_PREFIX, "https://jjrserver/");
    	configuration.add(CasSymbols.CAS_SERVICE, "http://localhost:8080/login");
    }
    
	public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration,
			SecurityFilterChainFactory factory, CasSecurityFilterChainFactory casFactory) {
		//configuration.add(factory.createChain("/index2").add(factory.anon()).build());
		configuration.add(factory.createChain("/login").add(casFactory.cas()).build());
		configuration.add(factory.createChain("/index2").add(casFactory.cas()).build());
	}
}
