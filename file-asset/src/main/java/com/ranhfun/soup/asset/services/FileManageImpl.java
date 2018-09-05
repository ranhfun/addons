package com.ranhfun.soup.asset.services;

import java.io.File;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

import com.ranhfun.soup.asset.FileAssetConstants;

public class FileManageImpl implements FileManage {

	private final Request request;
	
	private final AssetFactory assetFactory;
	
	private final BaseURLSource baseURLSource;
	
	private final String fullPlace;
	
	public FileManageImpl(
			@Inject 
			RequestGlobals requestGlobals,
			@Inject @FileProvider
			AssetFactory assetFactory,
			@Inject
			BaseURLSource baseURLSource,
			@Inject @Symbol(FileAssetConstants.FILE_ASSET_FULL_PLACE) 
			String fullPlace) {
		this.request = requestGlobals.getRequest();
		this.assetFactory = assetFactory;
		this.baseURLSource = baseURLSource;
		this.fullPlace = fullPlace;
	}
	
	public File createFloder(String floder) {
		File floder2 = new File(fullPlace + floder);
		if (!floder2.exists()) {
			floder2.mkdirs();
			return floder2;
		}
		return null;
	}
	
	public boolean renameFloder(String sourceFloder, String distFloder) {
		return new File(fullPlace + sourceFloder).renameTo(new File(fullPlace + distFloder));
	}

	public boolean deleteFloder(String floder) {
		return new File(fullPlace + floder).delete();
	}

	public File createFile(String fileName) {
		return new File(fullPlace + fileName);
	}

	public File createFileWithFloder(String floder, String fileName) {
		return new File(fullPlace + floder + fileName);
	}

	public boolean renameFile(String sourceFileName, String distFileName) {
		return new File(fullPlace + sourceFileName).renameTo(new File(fullPlace + distFileName));
	}

	public boolean renameFileWithFloder(String floder, String sourceFileName,
			String distFileName) {
		return new File(fullPlace + floder + sourceFileName).renameTo(new File(fullPlace + floder + distFileName));
	}

	public boolean deleteFile(String fileName) {
		return new File(fullPlace + fileName).delete();
	}

	public boolean deleteFileWithFloder(String floder, String fileName) {
		return new File(fullPlace + floder + fileName).delete();
	}

	public boolean moveFile(String sourceFloder, String distFloder,
			String fileName) {
		return new File(fullPlace + sourceFloder + fileName).renameTo(new File(fullPlace + distFloder + fileName));
	}

	public String getFloderLocalPath(String floder) {
		return new File(fullPlace + floder).getAbsolutePath();
	}

	public String getFileLocalPath(String fileName) {
		return new File(fullPlace + fileName).getAbsolutePath();
	}

	public String getFileLocalPath(String floder, String fileName) {
		return new File(fullPlace + floder + fileName).getAbsolutePath();
	}

	public String getFloderClientPath(String floder) {
		return getFloderAsset(floder).toClientURL();
	}

	public String getFileClientPath(String fileName) {
		return getFileAsset(fileName).toClientURL();
	}

	public String getFileClientPath(String floder, String fileName) {
		return getFileAsset(floder, fileName).toClientURL();
	}

	public String getFloderFullClientPath(String floder) {
		StringBuffer path = new StringBuffer();
		path.append(baseURLSource.getBaseURL(request.isSecure()));
		path.append(getFloderClientPath(floder));
		return path.toString();
	}

	public String getFileFullClientPath(String fileName) {
		StringBuffer path = new StringBuffer();
		path.append(baseURLSource.getBaseURL(request.isSecure()));
		path.append(getFileClientPath(fileName));
		return path.toString();
	}

	public String getFileFullClientPath(String floder, String fileName) {
		StringBuffer path = new StringBuffer();
		path.append(baseURLSource.getBaseURL(request.isSecure()));
		path.append(getFileClientPath(floder, fileName));
		return path.toString();
	}

	public File getFloder(String floder) {
		return new File(fullPlace + floder);
	}

	public File getFile(String fileName) {
		return new File(fullPlace + fileName);
	}

	public File getFile(String floder, String fileName) {
		return new File(fullPlace + fullPlace + fileName);
	}

	public Asset getFloderAsset(String floder) {
		return assetFactory.createAsset(new FileResource(fullPlace, floder));
	}

	public Asset getFileAsset(String fileName) {
		return assetFactory.createAsset(new FileResource(fullPlace, fileName));
	}

	public Asset getFileAsset(String floder, String fileName) {
		return assetFactory.createAsset(new FileResource(fullPlace + floder, fileName));
	}

	public String[] getFloderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getFileNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getFloders(String floder) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getFileNames(String floder) {
		// TODO Auto-generated method stub
		return null;
	}

	public File[] getFloderFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public File[] getFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public File[] getFolderFiles(String floder) {
		// TODO Auto-generated method stub
		return null;
	}

	public File[] getFiles(String floder) {
		// TODO Auto-generated method stub
		return null;
	}

}
