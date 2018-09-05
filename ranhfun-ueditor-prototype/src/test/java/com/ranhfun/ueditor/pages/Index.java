package com.ranhfun.ueditor.pages;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.ranhfun.ueditor.pages.Upload;

public class Index {

	@Persist
	@Property
	private String value;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	void onSuccess() {
		System.out.println(value);
	}
	
	public String getUploadPath() {
		Link link = pageRenderLinkSource.createPageRenderLink(Upload.class);
		link.addParameter("city", "1");
		return link.toRedirectURI();
	}
	
}
