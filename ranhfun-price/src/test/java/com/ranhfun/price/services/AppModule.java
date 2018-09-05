package com.ranhfun.price.services;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.price.jdbc.JDBCAdapter;
import com.ranhfun.price.jdbc.JDBCPersistentAdapter;
import com.ranhfun.price.jdbc.Statements;
import com.ranhfun.price.jdbc.adapter.MySqlJDBCAdapter;
import com.ranhfun.price.services.PriceModule;

@SubModule(PriceModule.class)
public class AppModule {
	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	}
	
    public static void contributePriceService(OrderedConfiguration<JDBCPersistentAdapter> configuration)
    {
    	
    	JDBCAdapter adapter = new MySqlJDBCAdapter();
		//adapter = new DefaultJDBCAdapter();
		adapter.setStatements(new Statements());
		/*DataSourceServiceSupport support = new DataSourceServiceSupport();
		support.setDataDirectory("price-data");*/
		Properties props=new Properties();
		props.put("driverClassName", "com.mysql.jdbc.Driver");
		props.put("url", "jdbc:mysql://localhost:3306/price");
		props.put("username", "root");
		props.put("password", "ranhfun.com");
		DataSource dataSource;
		try {
			dataSource = BasicDataSourceFactory.createDataSource(props);
	    	JDBCPersistentAdapter persistentAdapter = new JDBCPersistentAdapter(dataSource, adapter);
	    	configuration.add("PersistentAdapter", persistentAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}
