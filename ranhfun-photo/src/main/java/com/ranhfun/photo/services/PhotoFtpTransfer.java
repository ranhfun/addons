package com.ranhfun.photo.services;

public interface PhotoFtpTransfer {

	/**
	 * 文件上传
	 * @param sourceFloder 本地文件目录
	 * @param sourcePath 本地文件路径
	 * @param distPath FTP服务器文件路径
	 * @return
	 */
	public boolean transfer(String sourceFloder, String sourcePath, String distPath);
	
	/**
	 * 文件删除
	 * @param distPath FTP服务器文件路径
	 * @return
	 */
	public boolean delete(String distPath);
	
}
