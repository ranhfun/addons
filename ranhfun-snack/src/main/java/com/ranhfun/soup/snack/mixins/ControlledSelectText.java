package com.ranhfun.soup.snack.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.HeartbeatDeferred;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.MarkupWriterFactory;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.util.TextStreamResponse;

@Import(library={"${tapestry.scriptaculous}/controls.js", "controlledselecttext.js"})
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class ControlledSelectText {

    static final String EVENT_NAME = "selectautocompletetext";

    private static final String PARAM_NAME = "t:selecttext";

    @Parameter
    private Object[] context;
    
	@Parameter(name = "controller", required = true, defaultPrefix = BindingConstants.COMPONENT)
	private ClientElement controller;
    
    /**
     * The field component to which this mixin is attached.
     */
    @InjectContainer
    private ClientElement field;

    @Inject
    private ComponentResources resources;

    @Environmental
    private RenderSupport renderSupport;

    @Inject
    private Request request;

    @Inject
    private TypeCoercer coercer;

    @Inject
    private MarkupWriterFactory factory;

    @Inject
    private ResponseRenderer responseRenderer;


    /**
     * Mixin afterRender phrase occurs after the component itself. This is where we write the &lt;div&gt; element and
     * the JavaScript.
     *
     * @param writer
     */
    @HeartbeatDeferred
    void afterRender(MarkupWriter writer)
    {
        String id = field.getClientId();

        Link link = resources.createEventLink(EVENT_NAME,context);

        JSONObject config = new JSONObject();
        config.put("paramName", PARAM_NAME);

        // Let subclasses do more.
        configure(config);

        renderSupport.addInit("selectautocompletertext", new JSONArray(id, controller.getClientId(), link.toAbsoluteURI(), config));
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
    
    Object onSelectAutocompleteText(Object[] context)
    {
		String value = request.getParameter(PARAM_NAME);

        final Holder<String> matchesHolder = Holder.create();

        // Default it to an empty list.

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

        ContentType contentType = responseRenderer.findContentType(this);

        MarkupWriter writer = factory.newPartialMarkupWriter(contentType);

        return new TextStreamResponse(contentType.toString(), matchesHolder.get());
    }

    /**
     * Invoked to allow subclasses to further configure the parameters passed to the JavaScript Ajax.Autocompleter
     * options. The values minChars, frequency and tokens my be pre-configured. Subclasses may override this method to
     * configure additional features of the Ajax.Autocompleter.
     * <p/>
     * <p/>
     * This implementation does nothing.
     *
     * @param config parameters object
     */
    protected void configure(JSONObject config)
    {
    }
	
}
