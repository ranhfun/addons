package com.ranhfun.upload.pages;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.vfs.VFSAssetConstants;
import com.ranhfun.vfs.VFSProvider;
import com.ranhfun.vfs.services.VFSResource;

public class Index {

	@Inject
	@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
	@Property(write = false)
	private String fileFullPlace;
	
    @Inject
    private MultipartDecoder decoder;
    
	@Inject
	private HttpServletRequest request;
	
    @Inject
    private FileSystemManager fs;
    
    @VFSProvider
    @Inject
    private AssetFactory vfsAssetFactory;
    
    @Inject
    private ComponentResources resources;
    
    void onActivate() {
    	System.out.println(resources.createEventLink("upload").toRedirectURI());
    }
	
	Object onUpload() throws FileUploadException, FileSystemException {
	    String title = request.getParameter("pictitle");   //图片标题
	    String fileName = "";
		String state="SUCCESS";
		String dir = request.getParameter("dir");
		
		if(!ServletFileUpload.isMultipartContent(request)){
			return new TextStreamResponse("text/html", getError("请选择文件！"));
		}
		//检查目录
		File uploadDir = new File(fileFullPlace);
		fs.toFileObject(new File(fileFullPlace + "/" + dir)).createFolder();
		if(!uploadDir.isDirectory()){
			return new TextStreamResponse("text/html", getError("上传目录不存在！"));
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return new TextStreamResponse("text/html", getError("上传目录没有写权限！"));
		}
		UploadedFile uploaded = decoder.getFileUpload("picdata");
		if (uploaded!=null) {
			fileName = uploaded.getFileName();
			Pattern reg=Pattern.compile("[.]jpg|png|jpeg|gif$");
			Matcher matcher=reg.matcher(fileName);
			if(!matcher.find()) {
				state = "文件类型不允许！";
				return new TextStreamResponse("text/html", getError("文件类型不允许！"));
			}
			//检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = dir + "/" + df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			try{
 				File uploadedFile = new File(fileFullPlace, newFileName);
				uploaded.write(uploadedFile);
			}catch(Exception e){
				return new TextStreamResponse("text/html", getError("上传文件失败！"));
			}

			JSONObject obj = new JSONObject();
			obj.put("state", state);
			obj.put("title", title);
			obj.put("url", vfsAssetFactory.createAsset(new VFSResource(fs, fileFullPlace, newFileName)).toClientURL());
			return new TextStreamResponse("text/html", obj.toString());
		}
		return new TextStreamResponse("text/html", new JSONObject().toString());
	}
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("state", message);
		return obj.toString();
	}
	
	Object onImageUpload(UploadedFile uploadedFile) {
		System.out.println(uploadedFile.getFileName());
		return new TextStreamResponse("text/json", new JSONObject("success","1").toString());
	}
	
}
