package com.ranhfun.ueditor.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.vfs.VFSAssetConstants;
import com.ranhfun.vfs.VFSProvider;
import com.ranhfun.vfs.services.VFSResource;

public class Manage {

	@Inject
	@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
	@Property(write = false)
	private String fileFullPlace;
	
    @Inject
    private FileSystemManager fs;
    
    @VFSProvider
    @Inject
    private AssetFactory vfsAssetFactory;
	
	Object onActivate() {
		String imgStr ="";
		List<File> files = getFiles(new File(fileFullPlace).getAbsolutePath(), new ArrayList());
		for(File file :files ){
			imgStr+=vfsAssetFactory.createAsset(new VFSResource(fs, fileFullPlace, file.getPath().replace(new File(fileFullPlace).getAbsolutePath() + File.separator,""))).toClientURL()+"ue_separate_ue";
		}
		if(imgStr!=""){
	        imgStr = imgStr.substring(0,imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
	    }
		return new TextStreamResponse("text/plain", imgStr);
	}
	
	public List getFiles(String realpath, List files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for(File file :subfiles ){
				if(file.isDirectory()){
					getFiles(file.getAbsolutePath(),files);
				}else{
					if(!getFileType(file.getName()).equals("")) {
						files.add(file);
					}
				}
			}
		}
		return files;
	}

	public String getFileType(String fileName){
		String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while(type.hasNext()){
			String t = type.next();
			if(fileName.endsWith(t)){
				return t;
			}
		}
		return "";
	}
}
