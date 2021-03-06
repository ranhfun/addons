package com.ranhfun.soup.components;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;


/**
 * a "just in place" edit component that dont must emmbedded in a form.
 *
 * @version $Id: InPlaceEditor.java jquery version of chenillekit component from homburgs $
 */
@SupportsInformalParameters
@Import(library = {"jquery.jeditable.js",
				   "jeditable.js"})
@Events(InPlaceEditor.SAVE_EVENT)				   
public class InPlaceEditor implements ClientElement
{
	public final static String SAVE_EVENT = "save";

	/**
	 * The id used to generate a page-unique client-side identifier for the component. If a
	 * component renders multiple times, a suffix will be appended to the to id to ensure
	 * uniqueness. The uniqued value may be accessed via the
	 * {@link #getClientId() clientId property}.
	 */
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	/**
	 * The value to be read and updated. This is not necessarily a string, a translator may be provided to convert
	 * between client side and server side representations. If not bound, a default binding is made to a property of the
	 * container matching the component's id. If no such property exists, then you will see a runtime exception due to
	 * the unbound value parameter.
	 */
	@Parameter(required = true, principal = true)
	private String value;

	/**
	 * Size of the input text tag.
	 */
	@Parameter(value = "20", required = false, defaultPrefix = BindingConstants.LITERAL)
	private int size;
	
	/**
	 * width of the input text tag.
	 */
	@Parameter(value = "auto", required = false, defaultPrefix = BindingConstants.LITERAL)
	private String width;
	
	/**
	 * width of the input text tag.
	 */
	@Parameter(value = "auto", required = false, defaultPrefix = BindingConstants.LITERAL)
	private String height;

	/**
	 * The context for the link (optional parameter). This list of values will be converted into strings and included in
	 * the URI.
	 */
	@Parameter(required = false)
	private List<?> context;

	@Inject
	private ComponentResources resources;

	@Inject
	private Messages messages;

	@Environmental
	private JavaScriptSupport javascriptSupport;

	@Inject
	private Request request;

	private String assignedClientId;

	private Object[] contextArray;

	void setupRender()
	{
		assignedClientId = javascriptSupport.allocateClientId(clientId);
		contextArray = context == null ? new Object[0] : context.toArray();
	}

	void beginRender(MarkupWriter writer)
	{
		writer.element("span", "id", getClientId());

		if (value != null && value.length() > 0)
			writer.write(value);
		else
			writer.writeRaw(messages.get("empty"));
	}

	void afterRender(MarkupWriter writer)
	{
		Link link = resources.createEventLink(EventConstants.ACTION, contextArray);
		JSONObject spec = new JSONObject();

		writer.end();

		spec.put("clientId", getClientId());
		spec.put("href", link.toURI());
		JSONObject opts = new JSONObject();
		if (!height.equals("auto")) {
			opts.put("type", "textarea");
		} else {
			opts.put("type", "text");
		}
		opts.put("cancel", messages.get("cancelbutton"));
		opts.put("submit", messages.get("savebutton"));
		opts.put("tooltip", messages.get("tooltip"));
		opts.put("width", width);
		opts.put("height", height);
		spec.put("options", opts);

		
		javascriptSupport.addInitializerCall("editable", spec);
	}

	StreamResponse onAction(String value) throws UnsupportedEncodingException
	{
		String valueText = request.getParameter("value");

		resources.triggerEvent(SAVE_EVENT, new Object[]{value, valueText}, null);

		if (valueText == null || valueText.length() == 0)
			valueText = messages.get("empty");

		return new TextStreamResponse("text/html", new String(valueText.getBytes("UTF8")));
	}

	/**
	 * Returns a unique id for the element. This value will be unique for any given rendering of a page. This value is
	 * intended for use as the id attribute of the client-side element, and will be used with any DHTML/Ajax related
	 * JavaScript.
	 */
	public String getClientId()
	{
		return assignedClientId;
	}
}
