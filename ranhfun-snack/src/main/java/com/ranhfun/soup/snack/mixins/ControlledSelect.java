package com.ranhfun.soup.snack.mixins;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.data.BlankOption;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

@Import(library="controlledselect.js")
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class ControlledSelect {

    static final String EVENT_NAME = "selectautocomplete";

    private static final String PARAM_NAME = "controllerValue";

	@Parameter(name = "controller", required = true, defaultPrefix = BindingConstants.COMPONENT)
	private Field controller;
	
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String controlledBlankLabel;	
    
    @Parameter(value = "auto", defaultPrefix = BindingConstants.LITERAL)
    private BlankOption controlledBlankOption;    
	
    @InjectContainer
    private Field field;

    @Inject
    private ComponentResources resources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private Request request;
    
    @Parameter
    private Object[] context;

    void afterRender(MarkupWriter writer)
    {
        Link link = resources.createEventLink(EVENT_NAME, context);

        if (controller.getClientId()!=null) {
            JSONObject config = new JSONObject();
            config.put("controllerId", controller.getClientId());
            config.put("controlledId", field.getClientId());
            config.put("paramName", PARAM_NAME);
            config.put("url", link.toRedirectURI());
            
            config.put("showBlankOption", showBlankOption());
    		config.put("blankLabel", controlledBlankLabel);
    		if (config.isNull("blankLabel")) {
            	config.put("blankLabel", "");
    		}
            javaScriptSupport.addInitializerCall("controlledSelectRefresh", config);
		}
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
        String showBlankOption = request.getParameter("showBlankOption");
        String blankLabel = request.getParameter("blankLabel");
        CaptureResultCallback<String> callback = new CaptureResultCallback<String>();
        
        resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, newContext(context, value), callback);
        if (callback.getResult()==null && Boolean.valueOf(showBlankOption).booleanValue()) {
        	resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, newContext(context, value, blankLabel), callback);
		}
        if (callback.getResult()==null) {
        	resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, newContext(context, value, showBlankOption, blankLabel), callback);
		}

        return new TextStreamResponse("text/html", callback.getResult());
    }
    
    private boolean showBlankOption()
    {
        switch (controlledBlankOption)
        {
            case ALWAYS:
                return true;

            case NEVER:
                return false;

            default:
                return !field.isRequired();
        }
    } 
}
