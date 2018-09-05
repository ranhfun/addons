package com.ranhfun.proxool;

import java.util.ArrayList;
import java.util.List;

import com.ranhfun.proxool.Decryptool;
import com.ranhfun.proxool.DecryptoolImpl;
import com.ranhfun.proxool.Keytool;
import com.ranhfun.proxool.KeytoolImpl;
import com.ranhfun.proxool.ProxoolUtil;

public class MysqlTest {

	public static void main(String[] args) {
		List<Keytool> keytools = new ArrayList<Keytool>();
		List<Decryptool> decryptools = new ArrayList<Decryptool>();
		keytools.add(new KeytoolImpl());
		/*keytools.add(new KeytoolImpl() {
			public String getKey() {
				return "222";
			}
		});*/
		decryptools.add(new DecryptoolImpl());
		/*decryptools.add(new DecryptoolImpl() {
			public String sort() {
				return "333";
			}
		});*/
		// DES 加密文件
		// des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");
		// DES 解密文件
		// des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");
		String str1 = "piccweixin";
		//String str1 = "root";
		// DES 加密字符串
		String str2 = ProxoolUtil.entrypt(keytools, decryptools, str1);
		// DES 解密字符串
		String deStr = ProxoolUtil.decrypt(keytools, decryptools, str2);
		System.out.println(" piccweixin======= ");
		//System.out.println(" root======= ");
		System.out.println(" 加密前： " + str1);
		System.out.println(" 加密后： " + str2);
		System.out.println(" 解密后： " + deStr);

		String str12 = "G8XBaYhUj}b4JB,q";
		//String str12 = "dmos314";
		// DES 加密字符串
		String str22 = ProxoolUtil.entrypt(keytools, decryptools, str12);
		// DES 解密字符串
		String deStr2 = ProxoolUtil.decrypt(keytools, decryptools, str22);
		System.out.println(" G8XBaYhUj}b4JB,q======= ");
		//System.out.println(" dmos314======= ");
		System.out.println(" 加密前： " + str12);
		System.out.println(" 加密后： " + str22);
		System.out.println(" 解密后： " + deStr2);
	}

}
