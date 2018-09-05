package com.ranhfun.soup.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class Dialog {

    @Persist 
    private Integer count;

    @Component
    private org.apache.tapestry5.corelib.components.Zone myZone, myblockZone;

    @Inject
    private Request request;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;
    
    @Inject
    private Block myblock;

    @OnEvent(value = EventConstants.ACTIVATE)
    void init()
    {
        if (count == null)
            count = 0;
    }

    public Integer getCount()
    {
        return count++;
    }

    @OnEvent(value = EventConstants.ACTION)
    Object updateCount(Integer count)
    {
    	System.out.println(count);
        if (!request.isXHR()) { return this; }
        return myZone.getBody();
    }
    
    @Property
    private String goalName;
    
    List<String> onProvideCompletionsFromGoalName(String partial)
    {
        List<String> strings = new ArrayList<String>();
        if (partial != null && partial.startsWith("abc"))
        {
            strings.add("abdcdke");
            strings.add("hgfdhgfhgf");
            strings.add("jklhjkhl");
            strings.add("vcxcvcx");
        }

        return strings;
    }
    
    public JSONObject getOptions(){
    	return new JSONObject("draggable", "true");
    }
    
    void setupRender() {
    	javaScriptSupport.addScript(InitializationPriority.LATE, "check();");
    }
	
    public long getCurrentTime() {
    	return System.currentTimeMillis();
    }
    
    Object onActionFromRefreshZone() {
    	return myblockZone.getBody();
    }
    
	@OnEvent(value = EventConstants.ACTION, component = "blockLink")
	Object toPassTrade(Integer count) {
		return myblockZone.getBody();
	}
}
