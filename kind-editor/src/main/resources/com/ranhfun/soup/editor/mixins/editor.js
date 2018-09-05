// Copyright 2008 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.


// A class that attaches a confirmation box (with logic)  to
// the 'onclick' event of any HTML element.
// The source code taken from Tapestry 5 wiki. It is orginally written by Chris Lewis.
var Editor = Class.create();
Editor.prototype = {
        initialize: function(element,skinsPath,pluginsPath,uploadUrl) {
        	KE.lang['remote_keylink'] = "插入关键字索引";
			KE.plugin['remote_keylink'] = {
				click : function(id) {
					KE.util.selection(id);
					var dialog = new KE.dialog({
						id : id,
						cmd : 'remote_keylink',
						file : 'remote_keylink.html',
						width : 310,
						height : 90,
						title : KE.lang['remote_keylink'],
						yesButton : KE.lang['yes'],
						noButton : KE.lang['no']
					});
					dialog.show();
				},
				check : function(id) {
					var dialogDoc = KE.util.getIframeDoc(KE.g[id].dialog);
					var key = KE.$('key', dialogDoc).value;
					var title = KE.$('imgTitle', dialogDoc).value;

					if (key.length==0) {
						alert(KE.lang['pleaseInput']);
						window.focus();
						KE.g[id].yesButton.focus();
						return false;
					}
					if (title.length==0) {
						alert(KE.lang['pleaseInput']);
						window.focus();
						KE.g[id].yesButton.focus();
						return false;
					}
					
					return true;
				},
				exec : function(id) {
					KE.util.select(id);
					var iframeDoc = KE.g[id].iframeDoc;
					var dialogDoc = KE.util.getIframeDoc(KE.g[id].dialog);
					if (!this.check(id)) return false;
					var key = KE.$('key', dialogDoc).value;
					var title = KE.$('imgTitle', dialogDoc).value;
					this.insert(id, key, title);
				},
				insert : function(id, key, title) {
					var html = '<a target="_blank" href="javascript:void(0);" ';
					if (title) html += 'ref="' + title + '">';
					html += key + '</a>';
					KE.util.insertHtml(id, html);
					KE.layout.hide(id);
					KE.util.focus(id);
				}
			};
			KE.show({
				id : element,
                skinsPath: skinsPath,
                pluginsPath:pluginsPath,
                imageUploadJson : uploadUrl
				//items : ['source', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				//'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				//'insertunorderedlist', '|', 'emoticons', 'image', 'file', 'link', 'remote_keylink', 'about']
			});        	
        }
}
Tapestry.Initializer.kindeditor = function(elementId, skinsPath, pluginsPath, uploadUrl)
{
    $T(elementId).kindeditor = new Editor(elementId, skinsPath, pluginsPath, uploadUrl);
};
