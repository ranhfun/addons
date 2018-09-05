package com.ranhfun.soup.snack.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
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

@Import(library={"${tapestry.scriptaculous}/controls.js", "ajaxselect.js"})
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class AjaxSelect {

    static final String EVENT_NAME = "selectajaxcompleter";

    private static final String PARAM_NAME = "t:selecttext";

    @Parameter
    private Object[] context;
    
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
    void afterRender(MarkupWriter writer)
    {
        String id = field.getClientId();

        Link link = resources.createEventLink(EVENT_NAME,context);

        JSONObject config = new JSONObject();
        config.put("paramName", PARAM_NAME);

        // Let subclasses do more.
        configure(config);

        renderSupport.addInit("selectajaxcompleter", new JSONArray(id, link.toAbsoluteURI(), config));
    }

    void onSelectAjaxcompleter(Object[] context)
    {
		String value = request.getParameter(PARAM_NAME);
		if (context.length>0) {
			Object[] context1 = new Object[context.length+1];
			for (int i = 0; i < context.length; i++) {
				Object object = context[i];
				context1[i] = object;
			}
			context1[context.length] = value;
			context = context1;
		} else {
			context = new Object[]{value};
		}

        final Holder<String> matchesHolder = Holder.create();

        resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, context, null);
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
