package com.ranhfun.map.transform;

import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.ranhfun.map.MapConstants;
import com.ranhfun.map.annotations.ImportMap;

public class ImportMapWorker implements ComponentClassTransformWorker2
{
    private final JavaScriptSupport javaScriptSupport;
    
    private final AssetSource assetSource;
    
    private final String ak;

    public ImportMapWorker(JavaScriptSupport javaScriptSupport, AssetSource assetSource, @Symbol(MapConstants.BAIDU_CLIENT_AK)String ak)
    {
    	this.javaScriptSupport = javaScriptSupport;
    	this.assetSource = assetSource;
    	this.ak = ak;
    }

    public void transform(PlasticClass componentClass, TransformationSupport support, MutableComponentModel model)
    {
        processClassAnnotationAtSetupRenderPhase(componentClass, model);
    }

    private void processClassAnnotationAtSetupRenderPhase(PlasticClass componentClass, MutableComponentModel model)
    {
        final ImportMap annotation = componentClass.getAnnotation(ImportMap.class);

        if (annotation != null)
        {
            PlasticMethod setupRender = componentClass.introduceMethod(TransformConstants.SETUP_RENDER_DESCRIPTION);

            setupRender.addAdvice(new MethodAdvice()
            {
                public void advise(MethodInvocation invocation)
                {
                	if (annotation.quick()) {
                		javaScriptSupport.importJavaScriptLibrary(String.format("http://api.map.baidu.com/api?type=quick&ak=%s&v=1.0", ak));
					} else {
						javaScriptSupport.importJavaScriptLibrary(String.format("http://api.map.baidu.com/api?ak=%s&v=2.0", ak));
					}
                	if (annotation.lushu()) {
                		javaScriptSupport.importJavaScriptLibrary("http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js");
					}
                	invocation.proceed();
                }
            });

            model.addRenderPhase(SetupRender.class);
        }
    }
}
