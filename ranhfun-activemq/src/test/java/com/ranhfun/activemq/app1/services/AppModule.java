package com.ranhfun.activemq.app1.services;

import java.io.File;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;
import org.apache.activemq.store.jdbc.Statements;
import org.apache.activemq.store.jdbc.adapter.MySqlJDBCAdapter;
import org.apache.activemq.store.journal.JournalPersistenceAdapterFactory;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.activemq.services.ActiveMQModule;
import com.ranhfun.activemq.services.BrokerServiceManager;
import com.ranhfun.activemq.services.MQConnectionFactorySource;

@SubModule(ActiveMQModule.class)
public class AppModule {

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }
    
    @Contribute(BrokerServiceManager.class)
    public static void contributeBrokerServiceManager(
            final OrderedConfiguration<BrokerService> configuration) throws Exception
    {
    	BrokerService service = new BrokerService();
        service.setUseJmx(true);
        //service.setPersistent(false);
        
		Properties props=new Properties();
		props.put("driverClassName", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/activemq");
		props.put("username", "root");
		props.put("password", "ranhfun.com");
		DataSource datasource=BasicDataSourceFactory.createDataSource(props);
		
		JournalPersistenceAdapterFactory factory = new JournalPersistenceAdapterFactory();
		//factory.setJournalLogFiles(1);
		//factory.setJournalLogFileSize(32768);
		factory.setJournalLogFileSize(78);
		factory.setUseJournal(true);
		factory.setUseQuickJournal(true);
		factory.setDataSource(datasource);
		factory.setDataDirectory("activemq-data");
		factory.getStatements().setTablePrefix("app_");
		
		service.setPersistenceFactory(factory);
		//service.setDeleteAllMessagesOnStartup(true);
		/*
        JDBCPersistenceAdapter jdbc=new JDBCPersistenceAdapter();
		jdbc.setDataSource(datasource);
		jdbc.setUseDatabaseLock(false);
		
        service.setPersistenceAdapter(jdbc);
		jdbc.setDataDirectory(System.getProperty("user.dir")+File.separator+"data"+File.separator);
		jdbc.setAdapter(new MySqlJDBCAdapter());
		service.setPersistent(true);       */ 
        //service.setDataDirectory("activemq-data1");
        try {
			service.addConnector("tcp://localhost:61275");
			service.addNetworkConnector("static:(tcp://localhost:61276)");
		} catch (Exception e) {
			// should never occur
		}
		configuration.add("test1", service);
    }  
    
    @Contribute(MQConnectionFactorySource.class)
    public static void contributeMQConnectionFactorySource(
            final MappedConfiguration<String, String> configuration)
    {
        //configuration.add("test1", "vm://localhost?broker.persistent=false");
    	configuration.add("test1", "tcp://localhost:61275");
    }
	
}
