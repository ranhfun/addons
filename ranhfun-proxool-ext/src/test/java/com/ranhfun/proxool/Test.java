package com.ranhfun.proxool;

import java.math.BigInteger;

import com.ranhfun.proxool.DataEncryptUtil;

public class Test {

	public static void main(String[] args) {
		String inputStr = "简单加密";
		System.err.println("原文:\n" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code;
		try {
			code = DataEncryptUtil.encryptBASE64(inputData);
			System.err.println("BASE64加密后:\n" + code);

			byte[] output = DataEncryptUtil.decryptBASE64(code);
			String outputStr = new String(output);
			System.err.println("BASE64解密后:\n" + outputStr);
			System.err.println("验证base64加密解密一致性" + inputStr.equals(outputStr));

			String key = DataEncryptUtil.initMacKey();
			System.err.println("Mac密钥:\n" + key);
			DataEncryptUtil.encryptHMAC(inputData, key);
			BigInteger mac = new BigInteger(DataEncryptUtil.encryptHMAC(
					inputData, inputStr));
			System.err.println("HMAC:\n" + mac.toString(16));

			BigInteger md5 = new BigInteger(
					DataEncryptUtil.encryptMD5(inputData));
			System.err.println("MD5:\n" + md5.toString(16));

			BigInteger sha = new BigInteger(
					DataEncryptUtil.encryptSHA(inputData));
			System.err.println("SHA:\n" + sha.toString(32));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
