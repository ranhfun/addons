package com.ranhfun.soup.snack.mixins;

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
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.soup.snack.SoupEventConstants;

@Import(library={"${tapestry.scriptaculous}/controls.js", "ControllLink.js"})
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

        javaScriptSupport.addInitializerCall("controllLinkRefresh", new JSONArray(element.getClientId(), link.toRedirectURI()));
    }

    Object onSelectControllDisplay()
    {
        CaptureResultCallback<JSONArray> callback = new CaptureResultCallback<JSONArray>();

        resources.triggerEvent(SoupEventConstants.CONTROLL_DISPLAY, new Object[] { }, callback);

        return new TextStreamResponse("text/json", callback.getResult().toString());
    }
	
}
