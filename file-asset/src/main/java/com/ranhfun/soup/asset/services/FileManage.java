package com.ranhfun.soup.asset.services;

import java.io.File;

import org.apache.tapestry5.Asset;

public interface FileManage {

	public File createFloder(String floder);
	
	public boolean renameFloder(String sourceFloder, String distFloder);
	
	public boolean deleteFloder(String floder);
	
	public File createFile(String fileName);

	public File createFileWithFloder(String floder, String fileName);
	
	public boolean renameFile(String sourceFileName, String distFileName);
	
	public boolean renameFileWithFloder(String floder, String sourceFileName, String distFileName);
	
	public boolean deleteFile(String fileName);
	
	public boolean deleteFileWithFloder(String floder, String fileName);
	
	public boolean moveFile(String sourceFloder, String distFloder, String fileName);
	
	public String getFloderLocalPath(String floder);
	
	public String getFileLocalPath(String fileName);
	
	public String getFileLocalPath(String floder, String fileName);
	
	public String getFloderClientPath(String floder);
	
	public String getFileClientPath(String fileName);
	
	public String getFileClientPath(String floder, String fileName);
	
	public String getFloderFullClientPath(String floder);
	
	public String getFileFullClientPath(String fileName);
	
	public String getFileFullClientPath(String floder, String fileName);
	
	public File getFloder(String floder);
	
	public File getFile(String fileName);
	
	public File getFile(String floder, String fileName);
	
	public Asset getFloderAsset(String floder);
	
	public Asset getFileAsset(String fileName);
	
	public Asset getFileAsset(String floder, String fileName);
	
	public String[] getFloderNames();
	
	public String[] getFileNames();
	
	public String[] getFloders(String floder);
	
	public String[] getFileNames(String floder);
	
	public File[] getFloderFiles();
	
	public File[] getFiles();
	
	public File[] getFolderFiles(String floder);
	
	public File[] getFiles(String floder);
}
