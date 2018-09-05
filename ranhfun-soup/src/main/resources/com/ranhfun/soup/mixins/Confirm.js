(function( $ ) {
	$.extend(Tapestry.Initializer, {
		linkConfirm: function(element, message) {  
			var link = $("#" + element);
			link.click(function(event) {
	              if(! confirm(message))
	            	 event.preventDefault();
			});
		}
	});
}) ( jQuery );
