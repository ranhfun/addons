package com.ranhfun.proxool;

import com.ranhfun.proxool.DESUtil;

public class DesTest {

	public static void main(String[] args) {
	       DESUtil des = new DESUtil( "1234567" );
	       // DES 加密文件
	       // des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");
	       // DES 解密文件
	       // des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");
	       String str1 = " 要加密的字符串 test" ;
	       // DES 加密字符串
	       String str2 = des.encryptStr(str1);
	       // DES 解密字符串
	       String deStr = des.decryptStr(str2);
	       System. out .println( " 加密前： " + str1);
	       System. out .println( " 加密后： " + str2);
	       System. out .println( " 解密后： " + deStr);
	}
	
}
