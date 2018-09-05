package com.ranhfun.soup.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

@Import(library="ControlledInputText.js")
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class ControlledInputText {

    static final String EVENT_NAME = "selectautocomplete";

    private static final String PARAM_NAME = "controllerValue";

	@Parameter(name = "controller", required = true, defaultPrefix = BindingConstants.COMPONENT)
	private Field controller;
    
    @InjectContainer
    private ClientElement clientElement;

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
        config.put("controllerId", controller.getClientId());
        config.put("controlledId", clientElement.getClientId());
        config.put("paramName", PARAM_NAME);
        config.put("url", link.toRedirectURI());
        javaScriptSupport.addInitializerCall("controlledInputTextRefresh", config);
    }

    Object onSelectAutocomplete()
    {
        String value = request.getParameter(PARAM_NAME);
        CaptureResultCallback<String> callback = new CaptureResultCallback<String>();

        resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, new Object[] { value }, callback);

        return new TextStreamResponse("text/html", callback.getResult());
    }
}
