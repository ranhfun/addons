package com.ranhfun.soup.services.javascript;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

import com.ranhfun.jquery.services.utils.JQueryUtils;

/**
 * Resource stack for {@link AjaxUpload}.
 *
 * @author criedel
 */
public class AjaxUploadStack implements JavaScriptStack
{
    public static final String STACK_ID = "AjaxUploadStack";

    private final List<Asset> javaScriptStack;

    private final List<StylesheetLink> cssStack;

    public AjaxUploadStack(
            @Symbol(SymbolConstants.PRODUCTION_MODE)
            final boolean productionMode,
            final AssetSource assetSource)
    {

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        final String path = String.format("com/ranhfun/soup/components/upload/jquery.fileuploader%s.js", productionMode ? ".min" : "");

        javaScriptStack = F.flow(path).map(pathToAsset).toList();

        final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(pathToAsset,JQueryUtils.assetToStylesheetLink);
        cssStack = F.flow("com/ranhfun/soup/components/upload/fileuploader.css").map(pathToStylesheetLink).toList();
    }

    public String getInitialization()
    {
        return null;
    }

    public List<Asset> getJavaScriptLibraries()
    {
        return javaScriptStack;
    }

    public List<StylesheetLink> getStylesheets()
    {
        return cssStack;
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

}
