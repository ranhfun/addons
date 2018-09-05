package com.ranhfun.vfs.services;

import java.io.File;

import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;

@UsesOrderedConfiguration(VFSService.class)
public interface VFSService {

	public File retrieveFile(String path);
	
}
