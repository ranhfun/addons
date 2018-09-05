package com.ranhfun.soup.mixins;

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

@Import(library="ControlledSelect.js")
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class ControlledSelect {

    static final String EVENT_NAME = "selectautocomplete";

    private static final String PARAM_NAME = "controllerValue";

	@Parameter(name = "controller", defaultPrefix = BindingConstants.COMPONENT)
	private Field controller;
	
    @Parameter(defaultPrefix = BindingConstants.PROP)
    private String controllerId;	
    
    @Parameter(value = "false", defaultPrefix = BindingConstants.LITERAL)
    private boolean eager;	
	
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

    void afterRender(MarkupWriter writer)
    {
    	
        Link link = resources.createEventLink(EVENT_NAME);

        JSONObject config = new JSONObject();
        if (controllerId!=null) {
        	config.put("controllerId", controllerId);
		} else {
			config.put("controllerId", controller.getClientId());
		}
        config.put("controlledId", field.getClientId());
        config.put("paramName", PARAM_NAME);
        config.put("url", link.toRedirectURI());
        config.put("eager", eager);
        
        config.put("showBlankOption", showBlankOption());
		config.put("blankLabel", controlledBlankLabel);
		if (config.isNull("blankLabel")) {
        	config.put("blankLabel", "");
		}
        javaScriptSupport.addInitializerCall("controlledSelectRefresh", config);
    }

    Object onSelectAutocomplete()
    {
        String value = request.getParameter(PARAM_NAME);
        String showBlankOption = request.getParameter("showBlankOption");
        String blankLabel = request.getParameter("blankLabel");
        CaptureResultCallback<String> callback = new CaptureResultCallback<String>();

        resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, new Object[] { value }, callback);
        if (callback.getResult()==null && Boolean.valueOf(showBlankOption).booleanValue()) {
        	resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, new Object[] { value, blankLabel }, callback);
		}
        if (callback.getResult()==null) {
        	resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, new Object[] { value, showBlankOption, blankLabel }, callback);
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
