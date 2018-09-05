(function( $ ) {

	$.extend(Tapestry.Initializer, {
		controllSelectRefresh: function(params) {  
		  var controller = $("#" + params.controllerId);
		  controller.change(function() {
			  $.ajax({
					url: params.url,
					type: "POST",
					data: 'controllerValue='+controller.val(),
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