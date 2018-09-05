package com.ranhfun.soup.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.soup.SoupEventConstants;

@Import(library="ControllLink.js")
@Events(SoupEventConstants.CONTROLL_DISPLAY)
public class ControllLink {

	static final String EVENT_NAME = "selectcontrolldisplay";
	
    @InjectContainer
    private ClientElement element;

    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    void afterRender(MarkupWriter writer)
    {
        Link link = resources.createEventLink(EVENT_NAME);

        JSONObject config = new JSONObject();
        config.put("controllerId", element.getClientId());
        config.put("url", link.toRedirectURI());
        javaScriptSupport.addInitializerCall("controllLinkRefresh", config);
    }

    Object onSelectControllDisplay()
    {
        CaptureResultCallback<JSONArray> callback = new CaptureResultCallback<JSONArray>();

        resources.triggerEvent(SoupEventConstants.CONTROLL_DISPLAY, new Object[] { }, callback);

        return new TextStreamResponse("text/json", callback.getResult().toString());
    }
	
}
