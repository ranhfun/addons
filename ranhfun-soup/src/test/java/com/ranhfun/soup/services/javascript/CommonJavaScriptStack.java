package com.ranhfun.soup.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

import com.ranhfun.resource.asset.WebResource;
import com.ranhfun.soup.CommonConstants;
import com.ranhfun.soup.CommonProvider;

public class CommonJavaScriptStack implements JavaScriptStack {

	public static final String STACK_ID = "CommonJavaScriptStack";
    
    private final List<Asset> javaScriptStack;
	
    public CommonJavaScriptStack(
    		@Symbol(CommonConstants.COMMON_ASSET_SITE) final String site,
    		@CommonProvider final AssetFactory assetFactory, final AssetSource assetSource)
    {
    	
        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
            	if (path.startsWith("classpath:")) {
					return assetSource.getClasspathAsset(path);
				}
                return assetFactory.createAsset(new WebResource(site, path));
            }
        };
    	
    	javaScriptStack = F.flow(  
    			"style/js/jquery.js", "style/js/jquery.ui.js")
    			//"style/js/jquery.js", "classpath:common/jquery-ui-1.8.16.custom.min.js")
                .map(pathToAsset).toList();
    }
    
	public List<String> getStacks() {
		return Collections.emptyList();
	}

	public List<Asset> getJavaScriptLibraries() {
		
		return javaScriptStack;
	}

	public List<StylesheetLink> getStylesheets() {
		return Collections.emptyList();
	}

	public String getInitialization() {
		return null;
	}

}
