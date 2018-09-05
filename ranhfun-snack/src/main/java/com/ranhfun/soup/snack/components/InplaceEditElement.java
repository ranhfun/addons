package com.ranhfun.soup.snack.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.TextStreamResponse;

@Import(stylesheet="InplaceEditElement.css",library={"${tapestry.scriptaculous}/controls.js", "InplaceEditElement.js"})
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class InplaceEditElement implements ClientElement {

	static final String EVENT_NAME = "inplaceedit";
	
	private static final String PARAM_NAME = "t:inplaceedit";
	
    @Parameter
    private Object[] context;
    
    @Parameter(value = "1", defaultPrefix = BindingConstants.LITERAL)
    private String rows;
	
	@Inject
	private RenderSupport renderSupport;
	
	@Inject
	private Request request;
	
	private String clientId;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private Messages messages;
	
	public void beginRender(MarkupWriter writer) {
		clientId = renderSupport.allocateClientId(resources.getId());
		writer.element("span", "id", clientId);
		resources.renderInformalParameters(writer);
	}
	
	void afterRender(MarkupWriter writer) {
		
		writer.end(); // end span
		
		Link link = resources.createEventLink(EVENT_NAME, context);
		JSONObject config = new JSONObject();
		config.put("paramName", PARAM_NAME);
		config.put("okButton", "true");
		//config.put("okLink", "true");
		config.put("cancelButton", "true");
		config.put("okText", messages.get("save-label"));
		config.put("cancelText", messages.get("cancel-label"));
		config.put("savingText", messages.get("saving-label"));
		config.put("clickToEditText", messages.get("click-to-edit-text-label"));
		config.put("rows", rows);
		// Let subclasses do more.
        configure(config);
        
        renderSupport.addInit("inplaceeditelement", new JSONArray(clientId, link.toAbsoluteURI(), config));
	}
	
	Object onInplaceEdit(Object[] context) {
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
		
		resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, context, null);
		return new TextStreamResponse("text/plain",value);
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
	
	public String getClientId() {
		return clientId;
	}

}
