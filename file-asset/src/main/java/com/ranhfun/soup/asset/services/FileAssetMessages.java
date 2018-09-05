package com.ranhfun.soup.asset.services;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.MessagesImpl;

public class FileAssetMessages {

	private static final Messages MESSAGES = MessagesImpl.forClass(FileAssetMessages.class);
	
	public static String wrongAssetDigest(Resource resource) {
		return MESSAGES.format("wrong-asset-digest", resource.getPath());
	}
}
