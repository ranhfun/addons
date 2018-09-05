(function( $ ) {

	$.extend(Tapestry.Initializer, {
		controlledSelectTextRefresh: function(params) {  
		  var controller = $("#" + params.controllerId);
		  var controlled = $("#" + params.controlledId);
		  controller.change(function() {
			  $.ajax({
					url: params.url,
					type: "POST",
					data: 'controllerValue='+controller.val(),
					dataType: "html", 
					success: function(html){
						controlled.html(html);
					}
				});
		  });
		}
	});

}) ( jQuery );