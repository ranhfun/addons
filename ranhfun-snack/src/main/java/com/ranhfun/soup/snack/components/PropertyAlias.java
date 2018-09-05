// Copyright 2007, 2008, 2009 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.ranhfun.soup.snack.components;

import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.FieldTranslator;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.Core;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FieldTranslatorSource;
import org.apache.tapestry5.services.FieldValidatorDefaultSource;
import org.apache.tapestry5.services.FieldValidatorSource;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.PropertyEditContext;

/**
 * Used to show a single property of a bean. This is used primarily by {@link BeanExportForm}. Unlike BeanExportForm, the
 * object to be edited must already exist and the {@linkplain BeanModel model} must be passed in explicitly.
 */
public class PropertyAlias
{
    /**
     * Configures and stores a {@link PropertyCheckContext} into the {@link Environment}.
     */
    static class SetupEnvironment implements ComponentAction<PropertyAlias>
    {
		private static final long serialVersionUID = -7991075893672927560L;
		
		private final String property;

        public SetupEnvironment(String property)
        {
            this.property = property;
        }

        public void execute(PropertyAlias component)
        {
            component.setupEnvironment(property);
        }

        @Override
        public String toString()
        {
            return String.format("PropertyCheck.SetupEnvironment[%s]", property);
        }
    }

    static class CleanupEnvironment implements ComponentAction<PropertyAlias>
    {
        private static final long serialVersionUID = 7878694042753046523L;

        public void execute(PropertyAlias component)
        {
            component.cleanupEnvironment();
        }

        @Override
        public String toString()
        {
            return "PropertyCheck.CleanupEnvironment";
        }
    }

    private static final ComponentAction CLEANUP_ENVIRONMENT = new CleanupEnvironment();

    /**
     * The object to be edited by the BeanEditor. This will be read when the component renders and updated when the form
     * for the component is submitted. Typically, the container will listen for a "prepare" event, in order to ensure
     * that a non-null value is ready to be read or updated.
     */
    @Parameter(required = true, allowNull = false)
    private Object object;

    /**
     * Where to search for local overrides of property editing blocks as block parameters. This is normally the
     * containing component of the PropertyEditor, but when the component is used within a BeanEditor, it will be the
     * BeanEditor's block parameters that will be searched.
     */
    @Parameter(value = "this", allowNull = false)
    private PropertyOverrides overrides;

    /**
     * Identifies the property to be edited by the editor.
     */
    @Parameter(required = true)
    private String property;

    /**
     * The model that identifies the parameters to be edited, their order, and every other aspect.
     */
    @Parameter(required = true, allowNull = false)
    private BeanModel model;

    @Inject
    private FieldValidatorDefaultSource fieldValidatorDefaultSource;
    
    @Inject
    private FieldValidatorSource fieldValidatorSource;

    @Inject
    private Environment environment;

    /**
     * Source for property editor blocks. This defaults to the default implementation of {@link
     * org.apache.tapestry5.services.BeanBlockSource}.
     */
    @Parameter(required = true, allowNull = false)
    private BeanBlockSource beanBlockSource;
    
    @Parameter(required = true, allowNull = false)
    private Map<String, Object> propertyAliasMap;

    @Inject
    @Core
    private BeanBlockSource defaultBeanBlockSource;

    @Inject
    private Messages messages;

    @Inject
    private Locale locale;

    @Inject
    private ComponentResources resources;

    @Inject
    private FieldTranslatorSource fieldTranslatorSource;
    
    @Environmental
    private FormSupport formSupport;

    private PropertyModel propertyModel;

    BeanBlockSource defaultBeanBlockSource()
    {
        return defaultBeanBlockSource;
    }

    void setupEnvironment(final String propertyName)
    {
        propertyModel = model.get(propertyName);

        PropertyEditContext context = new PropertyEditContext()
        {
            public Messages getContainerMessages()
            {
                return overrides.getOverrideMessages();
            }

            public String getLabel()
            {
                return messages.get("property-alias");
            }

            public String getPropertyId()
            {
                return propertyModel.getId();
            }

            public Class getPropertyType()
            {
                return propertyModel.getPropertyType();
            }

            public Object getPropertyValue()
            {
                return propertyAliasMap.get(propertyModel.getPropertyName());
            }

            public FieldTranslator getTranslator(Field field)
            {
                return fieldTranslatorSource.createDefaultTranslator(field, propertyName,
                                                                     overrides.getOverrideMessages(), locale,
                                                                     String.class,
                                                                     propertyModel.getConduit());
            }

            public FieldValidator getValidator(Field field)
            {
                return new FieldValidator() {

					public boolean isRequired() {
						return false;
					}

					public void render(MarkupWriter writer) {
						
					}

					public void validate(Object value)
							throws ValidationException {
						
					}
                	
                };
            }

            public void setPropertyValue(Object value)
            {
            	propertyAliasMap.put(propertyModel.getPropertyName(), value);
            }

            public <T extends Annotation> T getAnnotation(Class<T> annotationClass)
            {
            	return propertyModel.getAnnotation(annotationClass);
            }
        };

        environment.push(PropertyEditContext.class, context);
    }

    /**
     * Called at the end of the form render (or at the end of the form submission) to clean up the {@link Environment}
     * stack.
     */
    void cleanupEnvironment()
    {
        environment.pop(PropertyEditContext.class);
    }

    /**
     * Record into the Form what's needed to process the property.
     */
    void setupRender()
    {
        // Sets up the PropertyEditContext for the duration of the render of this component
        // (which will include the duration of the editor block).

        formSupport.storeAndExecute(this, new SetupEnvironment(property));
    }

    /**
     * Records into the Form the cleanup logic for the property.
     */
    void cleanupRender()
    {
        // Removes the PropertyEditContext after this component (including the editor block)
        // has rendered.

        formSupport.storeAndExecute(this, CLEANUP_ENVIRONMENT);
    }

    /**
     * Returns a Block for rendering the property. The Block will be able to access the {@link PropertyEditContext} via
     * the {@link Environmental} annotation.
     */
    Block beginRender()
    {
        Block override = overrides.getOverrideBlock(propertyModel.getId());

        if (override != null)
        {
            return override;
        }


        return beanBlockSource.getEditBlock("text");
    }

    /**
     * Returns false, to prevent the rendering of the body of the component. PropertyEditor should not have a body.
     */
    boolean beforeRenderBody()
    {
        return false;
    }
}
