package com.ranhfun.soup.transform;

import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.FieldHandle;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.ranhfun.soup.annotations.ScrollClean;

public class ScrollCleanWorker implements ComponentClassTransformWorker2
{

	private final PropertyAccess propertyAccess;
	
    public ScrollCleanWorker(PropertyAccess propertyAccess)
    {
    	this.propertyAccess = propertyAccess;
    }
	
	public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model)
    {
        for (PlasticField field : plasticClass.getFieldsWithAnnotation(ScrollClean.class))
        {
            convertFieldIntoContainerScrollClean(plasticClass, support, field, model);
        }
    }

    private void convertFieldIntoContainerScrollClean(PlasticClass plasticClass, TransformationSupport support, 
    		PlasticField field, MutableComponentModel model)
    {
    	ScrollClean annotation = field.getAnnotation(ScrollClean.class);

        if (annotation != null)
        {
        	final FieldHandle handle = field.getHandle();
        	
        	
            PlasticMethod setupRender = plasticClass.introduceMethod(TransformConstants.SETUP_RENDER_DESCRIPTION);

            setupRender.addAdvice(new MethodAdvice()
            {
                public void advise(MethodInvocation invocation)
                {
                	propertyAccess.set(handle.get(invocation.getInstance()), "currentPage", 1);
                    invocation.proceed();
                }
            });        	
            model.addRenderPhase(SetupRender.class);
        }
    }
}
