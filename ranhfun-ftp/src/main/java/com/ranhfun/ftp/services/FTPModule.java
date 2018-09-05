package com.ranhfun.ftp.services;

import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.ftp.FTPConstants;
import com.ranhfun.vfs.VFSAssetConstants;
import com.ranhfun.vfs.services.VFSModule;

@SubModule(VFSModule.class)
public class FTPModule {

    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(FTPConstants.FTP_DIR, "ftproot");
        configuration.add(FTPConstants.FTP_PORT, "2221");
        configuration.add(FTPConstants.FTP_CONFIG_FILE, "myusers.properties");
        configuration.add(FTPConstants.FTP_ADMIN_NAME, "admin");
    }
    
    @Startup
    public static void initFtpServer(FileSystemManager fs, 
    		@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
        	String fileFullPlace, 
        	@Symbol(FTPConstants.FTP_PORT)
        	int port,
        	@Symbol(FTPConstants.FTP_CONFIG_FILE)
        	String configFile,
        	@Symbol(FTPConstants.FTP_ADMIN_NAME)
        	String adminName,
        	@Symbol(FTPConstants.FTP_DIR)
        	String dir,
        	RegistryShutdownHub shutdownHub)
    {
    	FtpServerFactory serverFactory = new FtpServerFactory();
        
    	ListenerFactory factory = new ListenerFactory();
    	        
    	// set the port of the listener
    	factory.setPort(port);

    	// replace the default listener
    	serverFactory.addListener("default", factory.createListener());
    	        
    	PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
    	userManagerFactory.setFile(new File(configFile));
    	
    	userManagerFactory.setAdminName(adminName);
    	userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
    	
    	serverFactory.setUserManager(userManagerFactory.createUserManager());
    	        
    	// start the server
    	final FtpServer server = serverFactory.createServer(); 
    	        
    	try {
			server.start();
			File file = new File(fileFullPlace, dir);
	    	FileObject fo = fs.toFileObject(file);
	    	if (!fo.exists()) {
				fo.createFolder();
			}
		} catch (FtpException e) {
			e.printStackTrace();
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		shutdownHub.addRegistryShutdownListener(new Runnable() {
			public void run() {
				server.stop();
			}
		});
		
    }
}
