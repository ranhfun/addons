package com.ranhfun.proxool;

public interface Decryptool {

	/**
	 * 排序
	 * @return
	 */
	public String sort();
	
	/**
	 * 解密
	 * @param key
	 * @param content
	 * @return
	 */
	public String decrypt(String key, String content);   
	
	/**
	 * 加密
	 * @param key
	 * @param content
	 * @return
	 */
	public String encrypt(String key, String content);

	
}
