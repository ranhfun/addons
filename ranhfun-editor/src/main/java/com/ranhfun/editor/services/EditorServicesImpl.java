package com.ranhfun.editor.services;

import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.ranhfun.editor.pages.UploadJson;

public class EditorServicesImpl implements EditorServices {

	private final AssetSource assetSource;
	private final PageRenderLinkSource pageRenderLinkSource;
	
	public EditorServicesImpl(AssetSource assetSource, PageRenderLinkSource pageRenderLinkSource) {
		this.assetSource = assetSource;
		this.pageRenderLinkSource = pageRenderLinkSource;
	}
	
	public String pluginsPath() {
		return assetSource.getClasspathAsset("kindeditor/plugins").toClientURL() + "/";
	}

	public String skinsPath() {
		return assetSource.getClasspathAsset("kindeditor/themes").toClientURL() + "/";
	}

	public String uploadPath() {
		return pageRenderLinkSource.createPageRenderLink(UploadJson.class).toRedirectURI();
	}

}
