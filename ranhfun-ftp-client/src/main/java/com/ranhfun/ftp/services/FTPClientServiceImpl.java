package com.ranhfun.ftp.services;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.InputStream;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.ranhfun.ftp.FTPAssetConstants;

public class FTPClientServiceImpl implements FTPClientService {

	@Inject @Symbol( value = FTPAssetConstants.FTP_SITE )
	private String site;
	
	@Inject @Symbol( value = FTPAssetConstants.FTP_PORT )
	private int port;
	
	@Inject @Symbol( value = FTPAssetConstants.FTP_USER_NAME )
	private String username;
	
	@Inject @Symbol( value = FTPAssetConstants.FTP_USER_PASSWORD )
	private String password;
	
	@Inject @Symbol( value = FTPAssetConstants.FTP_DEFAULT_DIR )
	private String dir;
	
	public FTPClient start() {
		return start(site, port, username, password);
	}

	public FTPClient start(String site) {
		return start(site, port, username, password);
	}
	
	public FTPClient start(String site, int port) {
		return start(site, port, username, password);
	}
	
	public FTPClient start(String username, String password) {
		return start(site, port, username, password);
	}
	
	public FTPClient start(String site, int port, String username, String password) {
		FTPClient client = new FTPClient();
		client.setType(FTPClient.TYPE_BINARY);
		try {
			client.connect(site.indexOf("http://")>-1?site.substring(7):site, port);
			client.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	public FTPClient upload(FTPClient client, File file) {
		return upload(client, dir, file);
	}
	
	public FTPClient upload(FTPClient client, String dir, File file) {
		try {
			client.changeDirectory(dir);
			client.upload(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}
	
	public FTPClient upload(FTPClient client, String filename, InputStream is) {
		return upload(client, dir, filename, is);
	}
	
	public FTPClient upload(FTPClient client, String dir, String filename, InputStream is) {
		try {
			client.changeDirectory(dir);
			client.upload(filename, is, 0, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return client;
	}
	
	public File download(FTPClient client, String remoteFile, File file) {
		return download(client, dir, file);
	}

	public File download(FTPClient client, String dir, String remoteFile, File file) {
		try {
			client.changeDirectory(dir);
			client.download(remoteFile, file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return file;
	}

	public void stop(FTPClient client) {
		try {
			client.disconnect(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isExistsDir(FTPClient client, String dir) {
		try {
			FTPFile[] files = client.list(dir);
			if (files.length == 1 && files[0].getType()==FTPFile.TYPE_DIRECTORY) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isExistsFile(FTPClient client, String filename) {
		return isExistsFile(client, dir, filename);
	}

	public boolean isExistsFile(FTPClient client, String dir, String filename) {
		try {
			client.changeDirectory(dir);
			FTPFile[] files = client.list(dir);
			if (files.length == 1 && files[0].getType()==FTPFile.TYPE_FILE) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	public boolean createDir(FTPClient client, String dir) {
		try {
			client.createDirectory(dir);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public FTPClient delete(FTPClient client, File file) {
		return delete(client, dir, file);
	}

	public FTPClient delete(FTPClient client, String dir, File file) {
		try {
			client.changeDirectory(dir);
			client.deleteFile(file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	public FTPClient delete(FTPClient client, String filename) {
		return delete(client, dir, filename);
	}

	public FTPClient delete(FTPClient client, String dir, String filename) {
		try {
			client.changeDirectory(dir);
			client.deleteFile(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

}
