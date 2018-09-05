package org.example.app1.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.example.app1.listener.ItemPostUpdateEventListener;
import org.hibernate.event.spi.EventType;

import com.ranhfun.jpa.services.JpaListener;
import com.ranhfun.jpa.services.ListenerManager;
import com.ranhfun.jpa.services.ListenerModule;

@SubModule(ListenerModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }

/*    @Contribute(ListenerManager.class)
    public static void provideListenerManager(
            final MappedConfiguration<EventType, Object> configuration)
    {
    	ItemPostUpdateEventListener listener = new ItemPostUpdateEventListener();
        configuration.add(EventType.POST_UPDATE, new ItemPostUpdateEventListener());
        //configuration.add(EventType.POST_UPDATE, new ItemPostUpdateEventListener());
        configuration.add(EventType.POST_INSERT, listener);
    }*/
    
    @Contribute(ListenerManager.class)
    public static void provideListenerManager(
            final OrderedConfiguration<JpaListener> configuration)
    {
    	ItemPostUpdateEventListener listener = new ItemPostUpdateEventListener();
        configuration.add("ItemPostUpdateEventListener.POST_UPDATE", new JpaListener(EventType.POST_UPDATE, new ItemPostUpdateEventListener()));
        //configuration.add("ItemPostUpdateEventListener.POST_UPDATE2", new JpaListener(EventType.POST_UPDATE, new ItemPostUpdateEventListener()));
        configuration.add("ItemPostUpdateEventListener.POST_INSERT", new JpaListener(EventType.POST_INSERT, listener));
    }
    
}
