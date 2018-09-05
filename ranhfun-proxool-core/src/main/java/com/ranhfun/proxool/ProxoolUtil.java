package com.ranhfun.proxool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

public class ProxoolUtil {

	private static ServiceLoader<Keytool> keytoolLoader = ServiceLoader.load(Keytool.class);
	private static ServiceLoader<Decryptool> decryptoolLoader = ServiceLoader.load(Decryptool.class);
	
	public static boolean decryptFlag() {
		return keytoolLoader.iterator().hasNext() && decryptoolLoader.iterator().hasNext();
	}
	
	public static String decrypt(String content) {
		if (content==null) {
			return null;
		}
		List<Keytool> keytools = new ArrayList<Keytool>();
		List<Decryptool> decryptools = new ArrayList<Decryptool>();
		for (Keytool keytool : keytoolLoader) {
			keytools.add(keytool);
		}
		for (Decryptool decryptool : decryptoolLoader) {
			decryptools.add(decryptool);
		}
		return decrypt(keytools, decryptools, content);
	}
	
	public static String entrypt(String content) {
		if (content==null) {
			return null;
		}
		List<Keytool> keytools = new ArrayList<Keytool>();
		List<Decryptool> decryptools = new ArrayList<Decryptool>();
		for (Keytool keytool : keytoolLoader) {
			keytools.add(keytool);
		}
		for (Decryptool decryptool : decryptoolLoader) {
			decryptools.add(decryptool);
		}
		return entrypt(keytools, decryptools, content);
	}
	
	public static String entrypt(List<Keytool> keytools, List<Decryptool> decryptools, String content) {
		Collections.sort(keytools, new Comparator<Keytool>() {
			public int compare(Keytool o1, Keytool o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		Collections.sort(decryptools, new Comparator<Decryptool>() {
			public int compare(Decryptool o1, Decryptool o2) {
				return o2.sort().compareTo(o1.sort());
			}
		});
		StringBuffer sb = new StringBuffer();
		for (Keytool keytool : keytools) {
			sb.append(keytool.getKey());
		}
		for (Decryptool decryptool : decryptools) {
			content = decryptool.encrypt(sb.toString(), content);
		}
		return content;
	}
	
	public static String decrypt(List<Keytool> keytools, List<Decryptool> decryptools, String content) {
		Collections.sort(keytools, new Comparator<Keytool>() {
			public int compare(Keytool o1, Keytool o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		Collections.sort(decryptools, new Comparator<Decryptool>() {
			public int compare(Decryptool o1, Decryptool o2) {
				return o1.sort().compareTo(o2.sort());
			}
		});
		StringBuffer sb = new StringBuffer();
		for (Keytool keytool : keytools) {
			sb.append(keytool.getKey());
		}
		for (Decryptool decryptool : decryptools) {
			content = decryptool.decrypt(sb.toString(), content);
		}
		return content;
	}
	
	
}
