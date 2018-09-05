(function( $ ) {

	$.extend(Tapestry.Initializer, {
		controllInputRefresh: function(params) {  
		  var controller = $("#" + params.controllerId);
		  var customer = $("#" + params.customerId);
		  var editable = params.editable;
		  var splitId = "";
		  if(params.controllerId.split('_').length>1) {
			  splitId = "_" + params.controllerId.split('_')[1];
		  }
		  controller.change(function() {
			  $.ajax({
					url: params.url,
					type: "POST",
					data: 'controllerValue='+customer.val(),
					dataType: "json", 
					success: function(json){
						$.each(json, function(i, item) {
							if($("#" + item.id + splitId).is("input")) {
								$("#" + item.id + splitId).val(item.value);
							} else if($("#" + item.id + splitId).is("select")) {
								$("#" + item.id + splitId).html($(item.value).unwrap().html());
							} else {
								$("#" + item.id + splitId).html(item.value);
							}
						});
					}
				});
		  });
		}
	});

}) ( jQuery );