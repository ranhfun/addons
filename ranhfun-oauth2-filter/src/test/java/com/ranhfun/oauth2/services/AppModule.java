package com.ranhfun.oauth2.services;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.slf4j.Logger;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.impl.SecurityFilterChain;

import com.ranhfun.oauth2.realm.OAuthRealm;
import com.ranhfun.oauth2.services.OAuthFilterModule;
import com.ranhfun.oauth2.services.OAuthSecurityFilterChainFactory;

@SubModule(OAuthFilterModule.class)
public class AppModule {

	@Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideApplicationDefaults(
            final MappedConfiguration<String, String> configuration)
    {
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
		configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
    }
	
	public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration,
			SecurityFilterChainFactory factory,
			OAuthSecurityFilterChainFactory oauthFactory) {
		//configuration.add(factory.createChain("/**").add(oauthFactory.oauth("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf055ad406c7c15ab&redirect_uri=http://sdo.swyun.cn/s/build&response_type=code&scope=snsapi_base&state=123#wechat_redirect")).build());
		configuration.add(factory.createChain("/**").add(oauthFactory.oauth("http://192.168.1.26:8080/index?code=11")).build());
	}
	
	@Contribute(WebSecurityManager.class)
	public static void contributeWebSecurityManager(Configuration<Realm> configuration, 
			Logger logger) {
		OAuthRealm adminRealm = new OAuthRealm();
		configuration.add(adminRealm);
	}
}
