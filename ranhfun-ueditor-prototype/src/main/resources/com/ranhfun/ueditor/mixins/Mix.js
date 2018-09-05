Tapestry.Editor = Class.create({
    initialize : function(specs) {
		var editor = new baidu.editor.ui.Editor({
			UEDITOR_HOME_URL:specs.url,
			imagePath:specs.imagePrefix,
			'imageUrl':specs.uploadUrl,
			'fileUrl':specs.fileUploadUrl,
			filePath:specs.imagePrefix,
			imageManagerPath:specs.managePrefix,
			'imageManagerUrl':specs.mamageUrl,
			'maxSize':specs.maxSize,
			'extParams':Object.toJSON(specs.extParams),
	        highlightJsUrl:specs.url + "third-party/SyntaxHighlighter/shCore.js",
	        highlightCssUrl:specs.url + "third-party/SyntaxHighlighter/shCoreDefault.css",	
	        toolbars: specs.toolbars,
	        initialFrameWidth:'100%'
		});
		editor.render(specs.element);
    }
});
Tapestry.Initializer.editorMix = function(specs) {
    new Tapestry.Editor(specs);
}