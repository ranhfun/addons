package com.ranhfun.soup.editor.mixins;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;

@Import(library={"classpath:kindeditor/kindeditor.js","editor.js"})
public class KindEditor {

    @Inject
    private RenderSupport renderSupport;
	
    @InjectContainer
    private ClientElement element;
    
    @Inject
    private LinkSource linkSource;
    
    @Inject
    @Path("classpath:kindeditor/skins/")
    private Asset skinsAsset;
    @Inject
    @Path("classpath:kindeditor/plugins/")
    private Asset pluginsAsset;
    
    @AfterRender
    public void afterRender() {
    	
//    	renderSupport.addInit("kindeditor",
//new JSONArray(element.getClientId(),element.getClientId(),skinsAsset.toClientURL() + "/",pluginsAsset.toClientURL() + "/",linkSource.createPageRenderLink("editor/uploadjson", false).toAbsoluteURI()));    	
    	renderSupport.addScript("$T('%s').kindeditor = new Editor('%s', '%s', '%s', '%s');",
    			element.getClientId(),element.getClientId(),skinsAsset.toClientURL() + "/",pluginsAsset.toClientURL() + "/",linkSource.createPageRenderLink("editor/uploadjson", false).toAbsoluteURI());
    }
	
}
