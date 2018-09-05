// Copyright 2007, 2008, 2009, 2010, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.ranhfun.soup.components;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SelectModelVisitor;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.mixins.RenderDisabled;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.internal.util.SelectModelRenderer;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FieldValidatorDefaultSource;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.ranhfun.jquery.services.utils.JQueryUtils;

/**
 * Select an item from a list of values, using an [X]HTML &lt;select&gt; element on the client side. Any validation
 * decorations will go around the entire &lt;select&gt; element.
 * <p/>
 * A core part of this component is the {@link ValueEncoder} (the encoder parameter) that is used to convert between
 * server-side values and unique client-side strings. In some cases, a {@link ValueEncoder} can be generated automatically from
 * the type of the value parameter. The {@link ValueEncoderSource} service provides an encoder in these situations; it
 * can be overriden by binding the encoder parameter, or extended by contributing a {@link ValueEncoderFactory} into the
 * service's configuration.
 *
 * @tapestrydoc
 */
@Events(
        {EventConstants.VALIDATE, EventConstants.VALUE_CHANGED + " when 'zone' parameter is bound"})
public class MultiSelect extends AbstractField
{
    public static final String CHANGE_EVENT = "change";

    private class Renderer extends SelectModelRenderer
    {

        public Renderer(MarkupWriter writer)
        {
            super(writer, encoder);
        }

        @Override
        protected boolean isOptionSelected(OptionModel optionModel, String clientValue)
        {
            return isSelected(clientValue);
        }
    }

    /**
     * A ValueEncoder used to convert the server-side object provided by the
     * "value" parameter into a unique client-side string (typically an ID) and
     * back. Note: this parameter may be OMITTED if Tapestry is configured to
     * provide a ValueEncoder automatically for the type of property bound to
     * the "value" parameter.
     *
     * @see ValueEncoderSource
     */
    @Parameter(required = true, allowNull = false)
    private ValueEncoder encoder;
    @Inject
    private ComponentDefaultProvider defaultProvider;
    
    @Parameter(value="2")
    private int selectedList;
    
    /**
     * The Dialog parameters (please refer to jquery-ui documentation)
     */
    @Parameter
    private JSONObject params;
    
    /**
     * The Tapestry.Initializer method to call to initialize the dialog.
     */
    @Parameter("literal:multiselect")
    private String initMethod;

    @Inject
    private AssetSource source;

    @Inject
    private JavaScriptSupport support;
    
    // Maybe this should default to property "<componentId>Model"?
    /**
     * The model used to identify the option groups and options to be presented to the user. This can be generated
     * automatically for Enum types.
     */
    @Parameter(required = true, allowNull = false)
    private SelectModel model;

    @Inject
    private Request request;

    @Inject
    private ComponentResources resources;

    @Environmental
    private ValidationTracker tracker;
    
    @Inject
    private ComponentResources componentResources;

    /**
     * Performs input validation on the value supplied by the user in the form submission.
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    private FieldValidator<Object> validate;

    /**
     * The value to read or update.
     */
    @Parameter(principal = true, autoconnect = true)
    private List<Object> selected;

    /**
     * Binding the zone parameter will cause any change of Select's value to be handled as an Ajax request that updates
     * the
     * indicated zone. The component will trigger the event {@link EventConstants#VALUE_CHANGED} to inform its
     * container that Select's value has changed.
     *
     * @since 5.2.0
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    @Inject
    private FieldValidationSupport fieldValidationSupport;

    @Environmental
    private FormSupport formSupport;

    @Inject
    private JavaScriptSupport javascriptSupport;

    @SuppressWarnings("unused")
    @Mixin
    private RenderDisabled renderDisabled;

    @Inject
    private Messages messages;
    
    private boolean isSelected(String clientValue)
    {
    	if (selected==null) {
			return false;
		}
    	for (Object selectedValue : selected) {
			if (TapestryInternalUtils.isEqual(clientValue, encoder.toClient(selectedValue))) {
				return true;
			}
		}
    	return false;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    protected void processSubmission(String controlName)
    {
    	 final String[] parameters = request.getParameters(controlName);
    	 
         List<Object> selected = this.selected;

         if (selected == null)
         {
             selected = CollectionFactory.newList();
         } else
         {
             selected.clear();
         }

         if (parameters != null)
         {
             for (final String value : parameters)
             {
                 final Object objectValue = encoder.toValue(value);

                 selected.add(objectValue);
             }

         }

         putPropertyNameIntoBeanValidationContext("selected");

         try
         {
             this.fieldValidationSupport.validate(selected, this.componentResources, this.validate);

             this.selected = selected;
         } catch (final ValidationException e)
         {
             this.tracker.recordError(this, e.getMessage());
         }

         removePropertyNameFromBeanValidationContext();
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    void beginRender(MarkupWriter writer)
    {
        writer.element("select", "name", getControlName(), "id", getClientId(), "multiple", "multiple");

        putPropertyNameIntoBeanValidationContext("selected");

        validate.render(writer);

        removePropertyNameFromBeanValidationContext();

        resources.renderInformalParameters(writer);

        decorateInsideField();

        // Disabled is via a mixin

        if (this.zone != null)
        {
            Link link = resources.createEventLink(CHANGE_EVENT);

            JSONObject spec = new JSONObject("selectId", getClientId(), "zoneId", zone, "url", link.toURI());

            javascriptSupport.addInitializerCall("linkSelectToZone", spec);
        }
    }

    @AfterRender
    void declareMultiSelect(MarkupWriter writer)
    {
        JSONObject data = new JSONObject();
        data.put("id", getClientId());

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("noneSelectedText", messages.get("none-selected-text"));
        defaults.put("checkAllText", messages.get("check-all-text"));
        defaults.put("uncheckAllText", messages.get("uncheck-all-text"));
        defaults.put("selectedText", messages.get("selected-text"));
        defaults.put("selectedList", selectedList);

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);

        configure(data);

        support.addInitializerCall(initMethod, data);
    }

    @AfterRender
    protected void addJSResources()
    {
        String[] scripts = { 
        		"com/ranhfun/soup/components/multiselect/jquery.multiselect.js",
        		"com/ranhfun/soup/components/multiselect/jquery.multiselect.filter.js",
        		"com/ranhfun/soup/components/multiselect/prettify.js",
        		"com/ranhfun/soup/components/multiselect/multiselect.js" };

        for (String path : scripts)
        {
            support.importJavaScriptLibrary(source.getClasspathAsset(path));
        }
    }

    @AfterRender
    protected void addCSSResources()
    {
        String[] stylesheets = { 
        		"com/ranhfun/soup/components/multiselect/jquery.multiselect.css",
        		"com/ranhfun/soup/components/multiselect/prettify.css" };

        for (String path : stylesheets)
        {
            support.importStylesheet(source.getClasspathAsset(path));
        }
    }

    protected void configure(JSONObject params)
    {
    }    
    
    Object onChange(@RequestParameter(value = "t:selectvalue", allowBlank = true)
                    final String[] selectedValue)
    {
        final List<Object> selected = CollectionFactory.newList();

        if (selectedValue != null)
        {
            for (final String value : selectedValue)
            {
                final Object objectValue = encoder.toValue(value);

                selected.add(objectValue);
            }

        }

        CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();

        this.resources.triggerEvent(EventConstants.VALUE_CHANGED, new Object[]
                {selected}, callback);

        this.selected = selected;

        return callback.getResult();
    }

    /**
     * Computes a default value for the "validate" parameter using {@link FieldValidatorDefaultSource}.
     */
    Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("selected", resources);
    }

    @BeforeRenderTemplate
    void options(MarkupWriter writer)
    {
        SelectModelVisitor renderer = new Renderer(writer);

        model.visit(renderer);
    }
    
    @Override
    public boolean isRequired()
    {
        return validate.isRequired();
    }
    
    Set<Object> getSelected()
    {
        if (selected == null)
        {
            return Collections.emptySet();
        }

        return CollectionFactory.newSet(selected);
    }
}
