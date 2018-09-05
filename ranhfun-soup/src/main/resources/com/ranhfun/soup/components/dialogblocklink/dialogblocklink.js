(function( $ ) {

    T5.extendInitializers(function(){

        function init(spec) {
            var element = spec.element;
            var dialogId = spec.dialogId;
            var url = spec.url;

            var dialog = $('#' + dialogId);

            $("#" + element).click(function(e) {

                e.preventDefault();
				$.ajax({
						url: url,
						type: "POST",
						dataType: "json", 
						success: function(reply){
						   var html = $.trim( reply.content );
						   dialog.html(html);
						   if (reply.inits) {
							   $.tapestry.utils.executeInits(reply.inits);
						   }
						   dialog.dialog('open');
						}
					}); 
                return false;
            });
        }

        return {
            dialogBlockLink : init
        };
    });

}) ( jQuery );
