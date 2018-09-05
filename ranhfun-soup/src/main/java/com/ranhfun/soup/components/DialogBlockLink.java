package com.ranhfun.soup.components;

import java.io.IOException;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@SupportsInformalParameters
public class DialogBlockLink extends DialogLink
{

    /**
     * The id of the zone to refresh.
     */
    @Parameter(required = true, defaultPrefix = BindingConstants.BLOCK)
    private Block block;
    
    /**
     * The activation context.
     */
    @Parameter
    private Object[] context;

    @Inject
    private ComponentResources resources;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;    

    @Inject
    private AssetSource source;

    private static final String[] scripts =
    { "com/ranhfun/soup/components/dialogblocklink/dialogblocklink.js" };

    @Inject
    private TypeCoercer typeCoercer;
    
    @Override
    @SetupRender
    void setJSInit()
    {
        setDefaultMethod("dialogBlockLink");
    }

    @Override
    @AfterRender
    void initJS(MarkupWriter writer)
    {
    	resources.renderInformalParameters(writer);
        writer.end();
        
        Link link = resources.createEventLink(EventConstants.ACTION + "2", context);
        
        JSONObject params = new JSONObject();
        params.put("element", getClientId());
        params.put("dialogId", getDialog());
        params.put("url", link.toAbsoluteURI());

        javaScriptSupport.addInitializerCall(getInitMethod(), params);
    }

    @Override
    @AfterRender
    protected void addJSResources()
    {
        for (String path : scripts)
        {
        	javaScriptSupport.importJavaScriptLibrary(source.getClasspathAsset(path));
        }
    }
    
    Object onAction2(EventContext context) throws IOException
    {
    	CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
    	resources.triggerContextEvent(EventConstants.ACTION, context, callback);
    	return block;
    }

}
