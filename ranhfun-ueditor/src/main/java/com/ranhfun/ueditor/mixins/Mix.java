package com.ranhfun.ueditor.mixins;

import java.util.Map;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.ranhfun.ueditor.ToolBarType;
import com.ranhfun.ueditor.UEditorConstants;
import com.ranhfun.ueditor.pages.FileUpload;
import com.ranhfun.ueditor.pages.Manage;
import com.ranhfun.ueditor.pages.Upload;
import com.ranhfun.ueditor.services.javascript.UEditorStack;

@Import(stack={UEditorStack.STACK_ID},library="Mix.js")
public class Mix {

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @InjectContainer
    private ClientElement element;
    
    @Inject
    private AssetSource assetSource;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String imagePrefix;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String serverUrl;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String fileServerUrl;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String managePrefix;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String manageUrl;

    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private ToolBarType toolbarType;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String toolbars;
    
    @Parameter(defaultPrefix=BindingConstants.LITERAL,value="10000")
    private int maxSize;
    
    @Parameter
    private Map<String, String> extParams;
    
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
    
    @Environmental
    private FormSupport formSupport;
    
    @Inject
    private BaseURLSource baseURLSource;

    @Inject
    @Symbol(UEditorConstants.EXTERNAL_MODE)
	private boolean externalMode;
    
    @AfterRender
    public void afterRender() {
    	String path = assetSource.getExpandedAsset("ueditor/dialogs").toClientURL();
    	JSONObject jso = new JSONObject();
    	jso.put("dir", "default");
    	if (extParams!=null) {
        	for (Map.Entry<String, String> entry : extParams.entrySet()) {
    			jso.put(entry.getKey(), entry.getValue());
    		}
		}
    	JSONObject data = new JSONObject();
    	data.put("form", formSupport.getClientId());
    	data.put("element", element.getClientId());
    	data.put("url", path.substring(0, path.length()-7));
    	if (imagePrefix==null) {
			if (externalMode) {
				imagePrefix = baseURLSource.getBaseURL(false);
			} else {
				imagePrefix = "";
			}
		}
    	data.put("imagePrefix", imagePrefix);
    	data.put("uploadUrl", serverUrl!=null?serverUrl:pageRenderLinkSource.createPageRenderLink(Upload.class).toRedirectURI());
    	data.put("fileUploadUrl", fileServerUrl!=null?fileServerUrl:pageRenderLinkSource.createPageRenderLink(FileUpload.class).toRedirectURI());
    	if (managePrefix==null) {
			if (externalMode) {
				managePrefix = baseURLSource.getBaseURL(false);
			} else {
				managePrefix = "";
			}
		}    	
    	data.put("managePrefix", managePrefix);
    	data.put("mamageUrl", manageUrl!=null?manageUrl:pageRenderLinkSource.createPageRenderLink(Manage.class).toRedirectURI());
    	data.put("maxSize", maxSize);
    	data.put("extParams", jso);
    	if (toolbars!=null) {
    		data.put("toolbars", new JSONArray(String.format("[[%s]]", toolbars)));
		} else {
			if (toolbarType==ToolBarType.ADV) {
				data.put("toolbars", new JSONArray(String.format("[[%s]]", 
					"'FullScreen','Source','Undo','Redo','|','RemoveFormat','FontFamily'," +
					"'FontSize','Bold','Italic','Underline','StrikeThrough','Paragraph','ForeColor'," +
					"'BackColor','|','InsertUnorderedList','InsertOrderedList','Indent','JustifyLeft','JustifyCenter'," +
					"'JustifyRight','JustifyJustify','|','Link','Emotion','|','InsertTable','|','InsertImage','InsertVideo','|'," +
					"'Preview'")));
			} else if (toolbarType==ToolBarType.BASIC) {
				data.put("toolbars", new JSONArray(String.format("[[%s]]", 
						"'Bold','Italic','Underline','|','RemoveFormat','InsertUnorderedList','InsertOrderedList','|'," +
						"'InsertImage','InsertVideo','|','BlockQuote'")));
			} else if (toolbarType==ToolBarType.ADVEST) {
				data.put("toolbars", new JSONArray(String.format("[[%s]]", 
						"'FullScreen','Source','Undo','Redo','|','RemoveFormat','FontFamily'," +
						"'FontSize','Bold','Italic','Underline','StrikeThrough','Paragraph','ForeColor'," +
						"'BackColor','|','InsertUnorderedList','InsertOrderedList','Indent','JustifyLeft','JustifyCenter'," +
						"'JustifyRight','JustifyJustify','|','Link','Emotion','|','InsertTable','|','InsertImage','InsertVideo'," +
						"'Attachment','|','PastePlain','WordImage','|','SearchReplace','Preview'")));
			}
		}
    	javaScriptSupport.addScript("var UEDITOR_HOME_URL='%s';", path.substring(0, path.length()-7));
    	javaScriptSupport.addInitializerCall("editorMix", data);
    }
    
}
