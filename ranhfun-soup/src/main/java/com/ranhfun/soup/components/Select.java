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

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.data.BlankOption;
import org.apache.tapestry5.corelib.mixins.RenderDisabled;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.internal.util.SelectModelRenderer;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;

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
public class Select extends AbstractField
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
    @Parameter
    private ValueEncoder encoder;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    // Maybe this should default to property "<componentId>Model"?
    /**
     * The model used to identify the option groups and options to be presented to the user. This can be generated
     * automatically for Enum types.
     */
    @Parameter(required = true, allowNull = false)
    private SelectModel model;

    /**
     * Controls whether an additional blank option is provided. The blank option precedes all other options and is never
     * selected. The value for the blank option is always the empty string, the label may be the blank string; the
     * label is from the blankLabel parameter (and is often also the empty string).
     */
    @Parameter(value = "auto", defaultPrefix = BindingConstants.LITERAL)
    private BlankOption blankOption;

    /**
     * The label to use for the blank option, if rendered. If not specified, the container's message catalog is
     * searched for a key, <code><em>id</em>-blanklabel</code>.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String blankLabel;

    @Inject
    private Request request;

    @Inject
    private ComponentResources resources;

    @Environmental
    private ValidationTracker tracker;

    /**
     * Performs input validation on the value supplied by the user in the form submission.
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    private FieldValidator<Object> validate;

    /**
     * The value to read or update.
     */
    @Parameter(required = true, principal = true, autoconnect = true)
    private Object value;

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

    private String selectedClientValue;

    @Parameter
    private Messages messages;
    
    private boolean isSelected(String clientValue)
    {
        return TapestryInternalUtils.isEqual(clientValue, selectedClientValue);
    }

    @SuppressWarnings(
            {"unchecked"})
    @Override
    protected void processSubmission(String controlName)
    {
        String submittedValue = request.getParameter(controlName);

        tracker.recordInput(this, submittedValue);

        Object selectedValue = toValue(submittedValue);

        putPropertyNameIntoBeanValidationContext("value");

        try
        {
            fieldValidationSupport.validate(selectedValue, resources, validate);

            value = selectedValue;
        } catch (ValidationException ex)
        {
            tracker.recordError(this, ex.getMessage());
        }

        removePropertyNameFromBeanValidationContext();
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    void beginRender(MarkupWriter writer)
    {
        writer.element("select", "name", getControlName(), "id", getClientId());

        putPropertyNameIntoBeanValidationContext("value");

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

    Object onChange(@RequestParameter(value = "t:selectvalue", allowBlank = true)
                    final String selectValue)
    {
        final Object newValue = toValue(selectValue);

        CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();

        this.resources.triggerEvent(EventConstants.VALUE_CHANGED, new Object[]
                {newValue}, callback);

        this.value = newValue;

        return callback.getResult();
    }

    protected Object toValue(String submittedValue)
    {
        return InternalUtils.isBlank(submittedValue) ? null : this.encoder.toValue(submittedValue);
    }

    @SuppressWarnings("unchecked")
    ValueEncoder defaultEncoder()
    {
        return defaultProvider.defaultValueEncoder("value", resources);
    }

    @SuppressWarnings("unchecked")
    SelectModel defaultModel()
    {
        Class valueType = resources.getBoundType("value");

        if (valueType == null)
            return null;

        if (Enum.class.isAssignableFrom(valueType))
            return new EnumSelectModel(valueType, resources.getContainerMessages());

        return null;
    }

    /**
     * Computes a default value for the "validate" parameter using {@link FieldValidatorDefaultSource}.
     */
    Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("value", resources);
    }

    Object retrieveBlankLabel()
    {
    	Messages containerMessages = resources.getContainerMessages();

        String key = resources.getId() + "-blanklabel";

        if (messages!=null && messages.contains(key)) {
			return messages.get(key);
		}
        
        if (containerMessages.contains(key))
            return containerMessages.get(key);

        return null;
    }

    /**
     * Renders the options, including the blank option.
     */
    @BeforeRenderTemplate
    void options(MarkupWriter writer)
    {
        selectedClientValue = tracker.getInput(this);

        // Use the value passed up in the form submission, if available.
        // Failing that, see if there is a current value (via the value parameter), and
        // convert that to a client value for later comparison.

        if (selectedClientValue == null)
            selectedClientValue = value == null ? null : encoder.toClient(value);

        if (showBlankOption())
        {
            writer.element("option", "value", "");
            if (blankLabel==null) {
				Object defaultBlankLabel = retrieveBlankLabel();
				if (defaultBlankLabel!=null) {
					writer.write((String)defaultBlankLabel);
				}
			} else {
				writer.write(blankLabel);
			}
            writer.end();
        }

        SelectModelVisitor renderer = new Renderer(writer);

        model.visit(renderer);
    }

    @Override
    public boolean isRequired()
    {
        return validate.isRequired();
    }

    public boolean showBlankOption()
    {
        switch (blankOption)
        {
            case ALWAYS:
                return true;

            case NEVER:
                return false;

            default:
                return !isRequired();
        }
    }
    
    public String getBlankLabel() {
    	return blankLabel;
    }

    // For testing.

    void setModel(SelectModel model)
    {
        this.model = model;
        blankOption = BlankOption.NEVER;
    }

    void setValue(Object value)
    {
        this.value = value;
    }

    void setValueEncoder(ValueEncoder encoder)
    {
        this.encoder = encoder;
    }

    void setValidationTracker(ValidationTracker tracker)
    {
        this.tracker = tracker;
    }

    void setBlankOption(BlankOption option, String label)
    {
        blankOption = option;
        blankLabel = label;
    }
}
