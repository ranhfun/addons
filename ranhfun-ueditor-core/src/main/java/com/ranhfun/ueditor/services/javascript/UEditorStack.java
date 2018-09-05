package com.ranhfun.ueditor.services.javascript;

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

public class UEditorStack implements JavaScriptStack
{
    public static final String STACK_ID = "UEditorStack";

    private final List<Asset> javaScriptStack;

    private final List<StylesheetLink> cssStack;

    public UEditorStack(
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

        final String path = String.format("ueditor/ueditor.all%s.js", productionMode ? ".min" : "");

        javaScriptStack = F.flow("ueditor/ueditor.config.js",path).map(pathToAsset).toList();

        final Mapper<String, StylesheetLink> pathToStylesheetLink = F.combine(pathToAsset, assetToStylesheetLink);
        cssStack = F.flow("ueditor/themes/default/css/ueditor.css").map(pathToStylesheetLink).toList();
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
    
    public static Mapper<Asset, StylesheetLink> assetToStylesheetLink = new Mapper<Asset, StylesheetLink>()
    {
        public StylesheetLink map(Asset input)
        {
            return new StylesheetLink(input);
        };
    };

}
