package com.ranhfun.ueditor.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Index {

	@Persist
	@Property
	private String value;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	void onSuccess() {
		System.out.println(value);
	}
	
}
