package com.ranhfun.soup.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.HeartbeatDeferred;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"classpath:com/ranhfun/soup/components/datetime/jquery-ui-timepicker-addon.js","classpath:com/ranhfun/soup/components/datetime/jquery-ui-timepicker-cn.js","DateTimeFeild.js"}, stylesheet={"classpath:com/ranhfun/soup/components/datetime/datepicker.css"})
@Events(EventConstants.VALIDATE)
public class DateTimeField extends AbstractField {

    /**
     * The value parameter of a DateField must be a {@link java.util.Date}.
     */
    @Parameter(required = true, principal = true, autoconnect = true)
    private Date value;

    /**
     * The format used to format <em>and parse</em> dates. This is typically specified as a string which is coerced to a
     * DateFormat. You should be aware that using a date format with a two digit year is problematic: Java (not
     * Tapestry) may get confused about the century.
     */
    @Parameter(value="yyyy-MM-dd", allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String format;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "false")
	private boolean timePicker;
    
    /**
     * The object that will perform input validation (which occurs after translation). The translate binding prefix is
     * generally used to provide this object in a declarative fashion.
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    @SuppressWarnings("unchecked")
    private FieldValidator<Object> validate;

    /**
     * Used to override the component's message catalog.
     *
     * @since 5.2.0.0
     */
    @Parameter("componentResources.messages")
    private Messages messages;

    @Environmental
    private JavaScriptSupport support;

    @Environmental
    private ValidationTracker tracker;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;

    @Inject
    private Locale locale;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Inject
    private FieldValidationSupport fieldValidationSupport;
    
    @Parameter(defaultPrefix=BindingConstants.COMPONENT)
    private Field fromField;
    
    @Parameter(defaultPrefix=BindingConstants.COMPONENT)
    private Field toField;

    /**
     * Computes a default value for the "validate" parameter using {@link ComponentDefaultProvider}.
     */
    final Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("value", resources);
    }

    void beginRender(MarkupWriter writer)
    {
        String value = tracker.getInput(this);

        if (value == null)
            value = formatCurrentValue();

        String clientId = getClientId();

        writer.element("input",

                "type", "text",

                "name", getControlName(),

                "id", clientId,

                "value", value);

        writeDisabled(writer);

        putPropertyNameIntoBeanValidationContext("value");

        validate.render(writer);

        removePropertyNameFromBeanValidationContext();

        resources.renderInformalParameters(writer);

        decorateInsideField();

        writer.end();
    }

    private void writeDisabled(MarkupWriter writer)
    {
        if (isDisabled())
            writer.attributes("disabled", "disabled");
    }

    private String formatCurrentValue()
    {
        if (value == null)
            return "";

        return new SimpleDateFormat(format).format(value);
    }

    @Override
    protected void processSubmission(String controlName)
    {
        String value = request.getParameter(controlName);

        tracker.recordInput(this, value);

        Date parsedValue = null;

        try
        {
            if (InternalUtils.isNonBlank(value))
                parsedValue = new SimpleDateFormat(format).parse(value);
        } catch (ParseException ex)
        {
            tracker.recordError(this, messages.format("date-value-not-parseable", value));
            return;
        }

        putPropertyNameIntoBeanValidationContext("value");
        try
        {
            fieldValidationSupport.validate(parsedValue, resources, validate);

            this.value = parsedValue;
        } catch (ValidationException ex)
        {
            tracker.recordError(this, ex.getMessage());
        }

        removePropertyNameFromBeanValidationContext();
    }
    
    @HeartbeatDeferred
    void afterRender() {
    	String from = null;
    	String to = null;
    	if (fromField!=null) {
    		from = fromField.getClientId();
		}
    	if (toField!=null) {
    		to = toField.getClientId();
		}
    	support.addInitializerCall("dateTime", new JSONArray(String.format("[%s,%s,%s,%s]", getClientId(),timePicker, from, to)));
    }

    void injectResources(ComponentResources resources)
    {
        this.resources = resources;
    }

    @Override
    public boolean isRequired()
    {
        return validate.isRequired();
    }
	
}
