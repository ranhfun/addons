package com.ranhfun.zip.util;

import java.util.List;

import com.ranhfun.zip.ZipResponse;

public abstract class AbstractZipResponse implements ZipResponse {

	private String basePath = null;
	private List<String> filePaths = null;
	private String fileName = "default";

	public AbstractZipResponse(String basePath, List<String> filePaths, String... args) {
		this.basePath = basePath;
		this.filePaths = filePaths;
		if (args != null && args.length > 0) {
			fileName = args[0];
		}
	}

	public String getBasePath() {
		return basePath;
	}
	public List<String> getFilePaths() {
		return filePaths;
	}
	
	public String getFileName() {
		return fileName;
	}

}
