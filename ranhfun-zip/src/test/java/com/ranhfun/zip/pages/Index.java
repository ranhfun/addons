package com.ranhfun.zip.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ranhfun.zip.util.AttachmentZipResponse;

public class Index {

	Object onActionFromAttachment() throws IOException {
		String basePath = "F:\\target\\weixin\\files\\Store";
		String path = "";
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("20140424231612_392.jpg");
		filePaths.add("20140507222633_843.jpg");
		return new AttachmentZipResponse(basePath, filePaths, "test");
	}
	
}
