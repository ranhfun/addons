(function( $ ) {
	$.extend(Tapestry.Initializer, {
		pageLinkPopup: function(element, url, windowName, features) {  
			var link = $("#" + element);
			link.click(function() {
			      var newWindow = window.open(url, windowName, windowName);
		          newWindow.focus();
			});
		}
	});
}) ( jQuery );	