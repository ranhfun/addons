package com.ranhfun.proxool;

import com.ranhfun.proxool.DESUtil;
import com.ranhfun.proxool.Decryptool;

public class DecryptoolImpl implements Decryptool {

	public String sort() {
		return DecryptoolImpl.class.getSimpleName();
	}
	
	public String decrypt(String key, String content) {
		DESUtil des = new DESUtil(key);
		return des.decryptStr(content);
	}

	public String encrypt(String key, String content) {
		DESUtil des = new DESUtil(key);
		return des.encryptStr(content);
	}

}
