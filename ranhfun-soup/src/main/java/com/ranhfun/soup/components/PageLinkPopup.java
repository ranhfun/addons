// Copyright 2008 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.


package com.ranhfun.soup.components;

import java.util.List;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library="PageLinkPopup.js")
public class PageLinkPopup implements ClientElement
{
    /** The logical name of the page to link to. */
    @Parameter(required = true, defaultPrefix = "literal")
    private String page;

    
    @Parameter(required=true, defaultPrefix = "literal")
    private String windowName;
    
    
    @Parameter(required=true, defaultPrefix = "literal")
    private String features;
    

	@Inject
	private PageRenderLinkSource pageRenderLinkResources;
	
    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    
    private String clientId;

    /**
     * If provided, this is the activation context for the target page (the information will be
     * encoded into the URL). If not provided, then the target page will provide its own activation
     * context.
     */
	@SuppressWarnings("rawtypes")
	@Parameter
    private List context;

    private final Object[] emptyContext = new Object[0];

    protected void beginRender(MarkupWriter writer)
    {
    	clientId = javaScriptSupport.allocateClientId(resources.getId());

    	writer.element("a", "href" , "javascript:void(0)",  "id", clientId);
    	  
        resources.renderInformalParameters(writer);
        
        Object[] activationContext = context != null ? context.toArray() : emptyContext;
                   
        Link link = pageRenderLinkResources.createPageRenderLinkWithContext(page,activationContext);
        
        javaScriptSupport.addInitializerCall("pageLinkPopup", new JSONArray(String.format("['%s','%s','%s','%s']", clientId,link.toURI(), windowName, features)));
    }

    protected void afterRender(MarkupWriter writer)
    {
        writer.end(); // <a>
        
    }

    public String getClientId()
    {
        return clientId;
    }

}
