(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			var conf = {
					source: function(request, response){
						
						var params = {
								//"extra": $("#" + specs.id).data('extra')
						};
						params[specs.paramName] = request.term;
						
						var ajaxRequest = {
	                    	url:specs.url,
	                        data:"data="+$.toJSON( params ), 
	                        dataType: "json", 
	                        type:"POST",
	                        success: function(data){
	                            response(eval(data));
	                        }
	                    };
	                    $.ajax(ajaxRequest);
	                },
	                messages: {
	                    noResults: '',
	                    results: function() {}
	                }
	        };
	        if (specs.delay >= 0) 
	        	conf.delay = specs.delay;
	            
	        if (specs.minLength >= 0) 
	        	conf.minLength = specs.minLength;
	        
	        var flag = true;
	        
	        if (specs.options) {
	        	var tempFocus = specs.options.focus;
	        	if (tempFocus) {
		        	$.extend(specs.options, {
		        		 focus: function( event, ui ) {
		        			eval("(" + tempFocus + ")(event, ui)");
		        	      }
		        	});
	        	}
	        	var tempSelect = specs.options.select;
	        	if (tempSelect) {
		        	$.extend(specs.options, {
		        	      select: function( event, ui ) {
		        	    	 eval("(" + tempSelect + ")(event, ui)");
		        	      }
		        	});
	        	}
	            $.extend(conf, specs.options);
	            
	            var tempRenderItem = specs.options.renderItem;
	            if (tempRenderItem) {
	            	$("#" + specs.id).autocomplete(conf).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
	            		return eval("(" + tempRenderItem + ")(ul, item)");
	 		    	};
	 		    	flag = false;
	            }
	            
	        }	  
	        if (flag) {
	        	$("#" + specs.id).autocomplete(conf);
	        }
	    }
		
		return {
			autocomplete : init
		}
	});
	
}) ( jQuery );