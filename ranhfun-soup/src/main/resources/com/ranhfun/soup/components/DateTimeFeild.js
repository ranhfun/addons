(function( $ ) {
	$.extend(Tapestry.Initializer, {
		dateTime: function(element, timePicker, from, to) {  
			if(timePicker) {
				$("#" + element).datetimepicker({
					dateFormat : 'yy-mm-dd',
					timeFormat: 'HH:mm',
					controlType: 'select',
					onClose:function(selectDateTime) {
						if(from!=null) {
							$( "#" + from ).datetimepicker( "option", "maxDate", selectDateTime );
						}
						if(to!=null) {
							 $( "#" + to ).datetimepicker( "option", "minDate", selectDateTime );
						}
					}});
				return ;
			} 
			$("#" + element).datepicker({
				dateFormat : 'yy-mm-dd',
				onClose:function(selectDate) {
					if(from!=null) {
						$( "#" + from ).datepicker( "option", "maxDate", selectDate );
					}
					if(to!=null) {
						 $( "#" + to ).datepicker( "option", "minDate", selectDate );
					}
				}});
		}
	});
}) ( jQuery );