package com.ranhfun.soup.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentEventCallback;
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
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

@Import(library="ControlledSelectText.js")
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class ControlledSelectText {

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
    
    @Parameter
    private Object[] context;
    
    @Inject
    private TypeCoercer coercer;

    void afterRender(MarkupWriter writer)
    {
        Link link = resources.createEventLink(EVENT_NAME, context);

        JSONObject config = new JSONObject();
        config.put("controllerId", controller.getClientId());
        config.put("controlledId", clientElement.getClientId());
        config.put("paramName", PARAM_NAME);
        config.put("url", link.toRedirectURI());
        javaScriptSupport.addInitializerCall("controlledSelectTextRefresh", config);
    }

    private Object[] newContext(Object[] context, Object...values) {
        Object[] newContext = null;
		if (context!=null && context.length>0) {
			Object[] context1 = new Object[context.length+values.length];
			for (int i = 0; i < context.length; i++) {
				Object object = context[i];
				context1[i] = object;
			}
			for (int i = 0; i < values.length; i++) {
				context1[context.length + i] = values[i];
			}
			newContext = context1;
		} else {
			newContext = values;
		}
		return newContext;
    }
    
    Object onSelectAutocomplete(Object[] context)
    {
        String value = request.getParameter(PARAM_NAME);
        
        final Holder<String> matchesHolder = Holder.create();
        
        ComponentEventCallback callback = new ComponentEventCallback()
        {
            public boolean handleResult(Object result)
            {
                String matches = coercer.coerce(result, String.class);

                matchesHolder.put(matches);

                return true;
            }
        };

        resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, newContext(context, value), callback);

        return new TextStreamResponse("text/html", matchesHolder.get());
    }
}
