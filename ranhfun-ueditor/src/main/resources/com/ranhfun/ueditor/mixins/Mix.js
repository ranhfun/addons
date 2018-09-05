var JSON = JSON || {};
JSON.stringify = JSON.stringify || function (obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {
        // simple data type
        if (t == "string") obj = '"'+obj+'"';
        return String(obj);
    }
    else {
        // recurse array or object
        var n, v, json = [], arr = (obj && obj.constructor == Array);
        for (n in obj) {
            v = obj[n]; t = typeof(v);
            if (t == "string") v = '"'+v+'"';
            else if (t == "object" && v !== null) v = JSON.stringify(v);
            json.push((arr ? "" : '"' + n + '":') + String(v));
        }
        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
};
(function( $ ) {
	$.extend(Tapestry.Initializer, {
		editorMix: function(specs) {  
			var editor = new baidu.editor.ui.Editor({
				UEDITOR_HOME_URL:specs.url,
				imagePath:specs.imagePrefix,
				'imageUrl':specs.uploadUrl,
				'fileUrl':specs.fileUploadUrl,
				filePath:specs.imagePrefix,
				imageManagerPath:specs.managePrefix,
				'imageManagerUrl':specs.mamageUrl,
				'maxSize':specs.maxSize,
				'extParams':JSON.stringify(specs.extParams),
		        highlightJsUrl:specs.url + "third-party/SyntaxHighlighter/shCore.js",
		        highlightCssUrl:specs.url + "third-party/SyntaxHighlighter/shCoreDefault.css",	
		        toolbars: specs.toolbars,
		        initialFrameWidth:'100%'
			});
			editor.render(specs.element);
		}
	});
}) ( jQuery );	
