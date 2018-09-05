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
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.soup.SoupEventConstants;

@Import(library="ControllSelect.js")
@Events(SoupEventConstants.CONTROLL_DISPLAY)
public class ControllSelect {

	static final String EVENT_NAME = "selectcontrolldisplay";
	
    private static final String PARAM_NAME = "controllerValue";
	
    @InjectContainer
    private ClientElement element;

    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private Request request;
    
    void afterRender(MarkupWriter writer)
    {
        Link link = resources.createEventLink(EVENT_NAME);

        JSONObject config = new JSONObject();
        config.put("controllerId", element.getClientId());
        config.put("url", link.toRedirectURI());
        javaScriptSupport.addInitializerCall("controllSelectRefresh", config);
    }

    Object onSelectControllDisplay()
    {
        String value = request.getParameter(PARAM_NAME);
        CaptureResultCallback<JSONArray> callback = new CaptureResultCallback<JSONArray>();

        resources.triggerEvent(SoupEventConstants.CONTROLL_DISPLAY, new Object[] { value }, callback);

        return new TextStreamResponse("text/json", callback.getResult().toString());
    }
	
}
