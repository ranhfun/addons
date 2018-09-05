package com.ranhfun.soup.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.messages.ComponentMessagesSource;

import com.ranhfun.jquery.JQueryConstants;

@SubModule({com.ranhfun.soup.services.SubModule.class})
public class AppModule {
	
    @Contribute(ComponentMessagesSource.class)
    public static void provideLibrayMessages(
            OrderedConfiguration<Resource> configuration,
            @Value("classpath:com/ranhfun/common/platform-common.properties")
            Resource coreCatalog)
    {
        configuration.add("PlatformCommon", coreCatalog, "before:AppCatalog");
    }
	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	       //configuration.add(JQueryConstants.SUPPRESS_PROTOTYPE, "false");
	}

	
}
