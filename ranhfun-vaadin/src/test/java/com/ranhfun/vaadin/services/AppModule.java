package com.ranhfun.vaadin.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.vaadin.services.UIDispatcher;
import com.ranhfun.vaadin.services.VaadinModule;
import com.ranhfun.vaadin.ui.DemoUI;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.ui.UI;

@SubModule(VaadinModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
    }
	
	@Contribute(UIDispatcher.class)
	public static void contributeUIDispatcher(OrderedConfiguration<UIDispatcher> configuration)
	{
		configuration.add("v", new UIDispatcher() {
			public Class<? extends UI> dispatcher(UIClassSelectionEvent event) {
				return DemoUI.class;
			}
		});
	}
    
}
