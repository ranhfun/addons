(function( $ ) {

	$.extend(Tapestry.Initializer, {
		controllLinkRefresh: function(params) {  
		  var controller = $("#" + params.controllerId);
		  controller.click(function() {
			  $.ajax({
					url: params.url,
					type: "POST",
					dataType: "json", 
					success: function(json){
						$.each(json, function(i, item) {
							$("#" + item.id).html(item.value);
						});
					}
				});
		  });
		}
	});

}) ( jQuery );