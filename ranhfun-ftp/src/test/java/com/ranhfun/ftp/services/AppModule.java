package com.ranhfun.ftp.services;

import java.io.IOException;
import java.util.Properties;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.ftp.services.FTPModule;

@SubModule(FTPModule.class)
public class AppModule {
	
    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void provideApplicationDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	/*ClasspathResource config = new ClasspathResource("config.properties");
    	Properties properties = new Properties();
    	try {
			properties.load(config.openStream());
		} catch (IOException e) {
			//
		}
    	configuration.add(FTPConstants.FTP_DIR, properties.getProperty("ftp.dir"));
        configuration.add(FTPConstants.FTP_PORT, properties.getProperty("ftp.port"));
        configuration.add(FTPConstants.FTP_CONFIG_FILE, properties.getProperty("ftp.config.file"));
        configuration.add(FTPConstants.FTP_ADMIN_NAME, properties.getProperty("ftp.admin.name"));*/
    }
	
}
