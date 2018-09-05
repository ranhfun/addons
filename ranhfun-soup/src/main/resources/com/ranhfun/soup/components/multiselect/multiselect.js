(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			 $("#" + specs.id).multiselect(specs.params); 
			 $("#" + specs.id).change(function() {
				 $("#" + specs.id).multiselect("refresh");
			 }); 
		}
		
		return {
			multiselect : init
		}
	});
	
}) ( jQuery );