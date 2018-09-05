package com.ranhfun.jquery.services.javascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.StylesheetLink;

import com.ranhfun.jquery.JQueryConstants;

public class JQueryJavaScriptStack implements JavaScriptStack {
	
	public static final String STACK_ID = "JQueryJavaScriptStack";

    private final boolean productionMode;
    
    private String jQueryAlias;

    private String jQueryUrl;
    
    private String jQueryUiUrl;
    
    private final List<Asset> jQueryJsStack;
    
    private final AssetSource assetSource;
    
    private final JavaScriptStackSource jsStackSource;
    
    private final boolean suppressPrototype;

    public JQueryJavaScriptStack(@Symbol(SymbolConstants.PRODUCTION_MODE)
                                 final boolean productionMode,
                                 
                                 @Symbol(JQueryConstants.JQUERY_ALIAS)
                                 final String jQueryAlias,
                                 
                                 @Symbol(JQueryConstants.JQUERY_URL)
                                 final String jQueryUrl,
                                 
                                 @Symbol(JQueryConstants.JQUERY_UI_URL)
                                 final String jQueryUiUrl,
                                 
                                 final AssetSource assetSource,
                                 
                                 final JavaScriptStackSource jsStackSrc,
                                 @Symbol(JQueryConstants.SUPPRESS_PROTOTYPE)
                                 final boolean suppressPrototype)
    {
        this.productionMode = productionMode;
        this.assetSource = assetSource;
        this.jQueryAlias = jQueryAlias;
        this.jQueryUrl = jQueryUrl;
        this.jQueryUiUrl = jQueryUiUrl;
        this.jsStackSource = jsStackSrc;
        this.suppressPrototype = suppressPrototype;
        
        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };
        
        jQueryJsStack = F.flow(
        				"${tapestry.jquery.path}/jquery-1.10.2" + (productionMode?".min":"") + ".js",
        				"${tapestry.jquery.path}/jquery-ui" + (productionMode?".min":"") + ".js",
		                "${tapestry.jquery.path}/jquery.json-2.2.js",
		                "${tapestry.jquery.path}/jquery-migrate-1.2.1" + (productionMode?".min":"") + ".js")
		                .map(pathToAsset).toList();
    }

    public String getInitialization()
    {
    	if(!suppressPrototype && jQueryAlias.equals("$")) jQueryAlias="$j";
        return productionMode ? "var "+jQueryAlias+" = jQuery;" : "var "+jQueryAlias+" = jQuery; Tapestry.DEBUG_ENABLED = true; var selector = new Array();";
    }

    /**
     * Asset in Prototype, have to be changed by a jQuery version
    */
    public Object chooseJavascript(Asset asset){
    	if(suppressPrototype)
    	{
			if(asset.getResource().getFile().endsWith("t5-prototype.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-jquery.js");
			}
			
			if(asset.getResource().getFile().endsWith("tapestry.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/tapestry-jquery.js");
			}
			if(asset.getResource().getFile().endsWith("t5-console.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-console-jquery.js");
			}
			if(asset.getResource().getFile().endsWith("t5-dom.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-dom-jquery.js");
			}
			if(asset.getResource().getFile().endsWith("t5-alerts.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-alerts-jquery.js");
			}
			if(asset.getResource().getFile().endsWith("t5-ajax.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-ajax-jquery.js");
			}
			if(asset.getResource().getFile().endsWith("tree.js"))
			{
				return this.assetSource.getExpandedAsset("${tapestry.jquery.path}/t5-tree-jquery.js");
			}
			if(asset.getResource().getFile().endsWith("prototype.js") || 
					asset.getResource().getFile().endsWith("scriptaculous.js") ||
					asset.getResource().getFile().endsWith("effects.js") || 
					asset.getResource().getFile().endsWith("exceptiondisplay.js"))
			{
				return null;
			}
    	}
    	return asset;
    }
    
    public List<Asset> getJavaScriptLibraries()
    {
    	List<Asset> ret = new ArrayList<Asset>();
    	
    	if (suppressPrototype) {
    	  	
    		String pathToTapestryJs = "${tapestry.jquery.path}/tapestry.js";
    	    Asset  tapestryJs = this.assetSource.getExpandedAsset(pathToTapestryJs);
    	    ret.add(tapestryJs);
		}
	
	    ret.addAll(jQueryJsStack);
	    
	    if (!suppressPrototype) {
	    	ret.add(this.assetSource.getExpandedAsset("${tapestry.jquery.path}/noconflict.js"));
		}
	    
    	for(Asset asset : jsStackSource.getStack(JQueryConstants.PROTOTYPE_STACK).getJavaScriptLibraries())
    	{
    		asset=(Asset) chooseJavascript(asset);
    		if(asset!=null) ret.add(asset);
    	}
	    
    	if(!suppressPrototype){
    		ret.add(this.assetSource.getExpandedAsset("${tapestry.jquery.path}/jquery-noconflict.js"));
    	}
    	
 		return ret;
    }

    public List<StylesheetLink> getStylesheets()
    {
    	List<StylesheetLink> ret = new ArrayList<StylesheetLink>();
    	
    	if(!suppressPrototype)
    	{
     		ret.addAll(jsStackSource.getStack(JQueryConstants.PROTOTYPE_STACK).getStylesheets());
    	}
    	else {
			for(StylesheetLink css : jsStackSource.getStack(JQueryConstants.PROTOTYPE_STACK).getStylesheets()){
				if(css.getURL().endsWith("t5-alerts.css") || css.getURL().endsWith("tapestry-console.css") ||
						css.getURL().endsWith("tree.css")) ret.add(css);
			}
    	}
 		return ret;
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }
	
}
