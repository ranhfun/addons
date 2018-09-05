package com.ranhfun.photo;

import com.ranhfun.ftp.FileInfo;
import com.ranhfun.ftp.FtpToolImpl;

public class PhotoTest {
	
	public static void main(String[] args) {
		FtpToolImpl tool = new FtpToolImpl();
		if(tool.init())
		{
			if(tool.connect())
			{
				 try {
					FileInfo file = new FileInfo();
					file.setDir("");//本地路径
					file.setFileName("f:/target/autopart/files/test.jpg");//本地文件名
					file.setFtpFilePath("autopart/files/test.jpg");//服务器路径
					tool.setFileName(file);
					tool.putFile();//开始传送
					tool.close();//关闭连接
				 } catch (Exception  e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
