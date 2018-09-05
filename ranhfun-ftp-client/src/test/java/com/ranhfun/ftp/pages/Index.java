package com.ranhfun.ftp.pages;

import it.sauronsoftware.ftp4j.FTPClient;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.ranhfun.ftp.FTPAssetConstants;
import com.ranhfun.ftp.FTPProvider;
import com.ranhfun.ftp.services.FTPClientService;
import com.ranhfun.ftp.services.FTPResource;

public class Index {

	@FTPProvider
	@Inject
	private AssetFactory assetFactory;
	
	@Inject 
	@Symbol(FTPAssetConstants.FTP_SITE)
	private String site;
	
    @Property
    private UploadedFile imageFile;
	
    @Inject
    private FTPClientService ftpClientService;
    
	public String getPath() {
		return assetFactory.createAsset(new FTPResource(site, "shop/test2.jpg")).toClientURL();
	}
	
	public void onSuccess() {
		if (imageFile!=null) {
			FTPClient client = ftpClientService.start();
			ftpClientService.upload(client, "test2.jpg", imageFile.getStream());
			ftpClientService.stop(client);
		}
	}
	
}
