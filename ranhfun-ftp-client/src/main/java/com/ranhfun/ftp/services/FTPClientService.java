package com.ranhfun.ftp.services;

import java.io.File;
import java.io.InputStream;

import it.sauronsoftware.ftp4j.FTPClient;

public interface FTPClientService {

	public FTPClient start();
	
	public FTPClient start(String site);
	
	public FTPClient start(String site, int port);
	
	public FTPClient start(String username, String password);
	
	public FTPClient start(String site, int port, String username, String password);
	
	public FTPClient upload(FTPClient client, File file);
	
	public FTPClient upload(FTPClient client, String dir, File file);
	
	public FTPClient upload(FTPClient client, String filename, InputStream is);
	
	public FTPClient upload(FTPClient client, String dir, String filename, InputStream is);
	
	public File download(FTPClient client, String remoteFile, File file);
	
	public File download(FTPClient client, String dir, String remoteFile, File file);
	
	public void stop(FTPClient client);
	
	public boolean isExistsDir(FTPClient client, String dir);
	
	public boolean isExistsFile(FTPClient client, String filename);
	
	public boolean isExistsFile(FTPClient client, String dir, String filename);
	
	public boolean createDir(FTPClient client, String dir);
	
	public FTPClient delete(FTPClient client, File file);
	
	public FTPClient delete(FTPClient client, String dir, File file);
	
	public FTPClient delete(FTPClient client, String filename);
	
	public FTPClient delete(FTPClient client, String dir, String filename);
	
}
