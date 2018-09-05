package com.ranhfun.zip;

import java.util.List;


public interface ZipResponse {

	String getFileName();
	
	String getBasePath();
	
	List<String> getFilePaths();
	
}
