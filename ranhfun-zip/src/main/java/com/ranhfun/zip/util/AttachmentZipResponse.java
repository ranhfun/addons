package com.ranhfun.zip.util;

import java.util.List;

public class AttachmentZipResponse extends AbstractZipResponse {

	public AttachmentZipResponse(String basePath, List<String> filePaths, String... args) {
		super(basePath, filePaths, args);
	}

}
