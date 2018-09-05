package com.ranhfun.soup.editor.services;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.internal.util.MessagesImpl;

public class EditorMessages {

	private static final Messages MESSAGES = MessagesImpl.forClass(EditorMessages.class);
	
	public static String fileNotChoose() {
		return MESSAGES.get("file-not-choose");
	}
	
	public static String uploadDirectoryNotExists() {
		return MESSAGES.get("upload-directory-not-exists");
	}
	
	public static String uploadDirectoryCantWrite() {
		return MESSAGES.get("upload-directory-can-not-write");
	}
	
	public static String fileSizeTooLarge() {
		return MESSAGES.get("file-size-too-large");
	}
	
	public static String fileSuffixNotAllow() {
		return MESSAGES.get("file-suffix-not-allow");
	}
	
	public static String fileUploadFail() {
		return MESSAGES.get("file-upload-fail");
	}
}
