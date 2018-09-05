(function( $ ) {
	$.extend(Tapestry.Initializer, {
		controlledSelectRefresh: function(params) {  
		  var controller = $("#" + params.controllerId);
		  var controlled = $("#" + params.controlledId);
		  controller.change(function() {
			  eagerRefresh(params, controller, controlled);
		  });
		  if(params.eager) {
			  eagerRefresh(params, controller, controlled);
		  };
		}
	});

}) ( jQuery );

function eagerRefresh(params, controller, controlled) {
	  $.ajax({
			url: params.url,
			type: "POST",
			data: 'controllerValue='+controller.val()+'&showBlankOption='+params.showBlankOption+'&blankLabel=' + params.blankLabel,
			dataType: "html", 
			success: function(html){
				controlled.html($(html).unwrap().html());
				controlled.trigger("change");
			}
		});
}