package com.ranhfun.photo.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

//import com.ranhfun.ftp.FileInfo;
//import com.ranhfun.ftp.FtpToolImpl;
import com.ranhfun.photo.PhotoConstants;

public class PhotoFtpTransferImpl implements PhotoFtpTransfer {

	private final String ftpFloder;
	
	public PhotoFtpTransferImpl(
			@Inject @Symbol(PhotoConstants.PHOTO_FTP_FOLDER)
			String ftpFloder) {
		this.ftpFloder = ftpFloder;
	}
	
	public boolean transfer(String sourceFloder, String sourcePath, String distPath) {
//		FtpToolImpl tool = new FtpToolImpl();
//		if(tool.init())
//		{
//			if(tool.connect())
//			{
//				 try {
//					FileInfo file = new FileInfo();
//					file.setDir(sourceFloder);//本地路径
//					file.setFileName(sourcePath);//本地文件名
//					file.setFtpFilePath(ftpFloder + distPath);//服务器路径
//					tool.setFileName(file);
//					tool.putFile();//开始传送
//					tool.close();//关闭连接
//				 } catch (Exception  e) {
//					// transfer fail
//					return false;
//				}
//			}
//		}
		return true;
	}

	public boolean delete(String distPath) {
		boolean flag = false;
//		FtpToolImpl tool = new FtpToolImpl();
//		if(tool.init())
//		{
//			if(tool.connect())
//			{
//				 try {
//					flag = tool.delFile(ftpFloder + distPath);//开始传送
//					tool.close();//关闭连接
//				 } catch (Exception  e) {
//					// transfer fail
//					return false;
//				}
//			}
//		}
		return flag;
	}

}
