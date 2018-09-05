package com.ranhfun.soup.editor.pages;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.soup.asset.FileAssetConstants;
import com.ranhfun.soup.asset.services.FileAssetPath;
import com.ranhfun.soup.editor.EditorConstants;
import com.ranhfun.soup.editor.services.EditorMessages;

public class UploadJson {

	@Inject
	@Symbol(FileAssetConstants.FILE_ASSET_FULL_PLACE)
	@Property(write = false)
	private String fullpath;
	
	@Inject
	@Symbol(EditorConstants.KIND_EDITOR_FILE_TYPES)
	@Property(write = false)
	private String fileTypes;
	
	@Inject
	@Symbol(EditorConstants.KIND_EDITOR_FILE_MAX_SIZE)
	@Property(write = false)
	private String maxSize;
	
    @Inject
    private MultipartDecoder decoder;
    
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private FileAssetPath fileAssetPath;
	
	Object onActivate() throws FileUploadException {
		if(!ServletFileUpload.isMultipartContent(request)){
			return new TextStreamResponse("text/html", getError(EditorMessages.fileNotChoose()));
		}
		//检查目录
		File uploadDir = new File(fullpath);
		if(!uploadDir.isDirectory()){
			return new TextStreamResponse("text/html", getError(EditorMessages.uploadDirectoryNotExists()));
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return new TextStreamResponse("text/html", getError(EditorMessages.uploadDirectoryCantWrite()));
		}
		UploadedFile uploaded = decoder.getFileUpload("imgFile");
		if (uploaded!=null) {
			String fileName = uploaded.getFileName();
			//检查文件大小
			if(uploaded.getSize() > Long.parseLong(maxSize)){
				return new TextStreamResponse("text/html", getError(EditorMessages.fileSizeTooLarge()));
			}
			//检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(fileTypes.split(",")).contains(fileExt)){
				return new TextStreamResponse("text/html", getError(EditorMessages.fileSuffixNotAllow()));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			try{
				File uploadedFile = new File(fullpath, newFileName);
				uploaded.write(uploadedFile);
			}catch(Exception e){
				return new TextStreamResponse("text/html", getError(EditorMessages.fileUploadFail()));
			}

			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", fileAssetPath.getAsset(newFileName).toClientURL());
			return new TextStreamResponse("text/html", obj.toString());
		}
		return new TextStreamResponse("text/html", new JSONObject().toString());
	}
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}
	
}
