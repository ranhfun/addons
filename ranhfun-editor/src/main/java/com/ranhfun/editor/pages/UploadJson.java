package com.ranhfun.editor.pages;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.editor.EditorConstants;
import com.ranhfun.vfs.VFSAssetConstants;
import com.ranhfun.vfs.VFSProvider;
import com.ranhfun.vfs.services.VFSResource;

public class UploadJson {

	@Inject
	@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
	@Property(write = false)
	private String fileFullPlace;
	
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
    private FileSystemManager fs;
    
    @VFSProvider
    @Inject
    private AssetFactory vfsAssetFactory;
	
	Object onActivate() throws FileUploadException {
		if(!ServletFileUpload.isMultipartContent(request)){
			return new TextStreamResponse("text/html", getError("请选择文件！"));
		}
		//检查目录
		File uploadDir = new File(fileFullPlace);
		if(!uploadDir.isDirectory()){
			return new TextStreamResponse("text/html", getError("上传目录不存在！"));
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return new TextStreamResponse("text/html", getError("上传目录没有写权限！"));
		}
		UploadedFile uploaded = decoder.getFileUpload("imgFile");
		if (uploaded!=null) {
			String fileName = uploaded.getFileName();
			//检查文件大小
			if(uploaded.getSize() > Long.parseLong(maxSize)){
				return new TextStreamResponse("text/html", getError("上传文件超出最大限制！"));
			}
			//检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(fileTypes.split(",")).contains(fileExt)){
				return new TextStreamResponse("text/html", getError("上传文件扩展名不允许！"));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			try{
				File uploadedFile = new File(fileFullPlace, newFileName);
				uploaded.write(uploadedFile);
			}catch(Exception e){
				return new TextStreamResponse("text/html", getError("上传文件失败！"));
			}

			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", vfsAssetFactory.createAsset(new VFSResource(fs, fileFullPlace, newFileName)).toClientURL());
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
