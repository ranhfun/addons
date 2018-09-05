(function( $ ) {
	var position = {x:0, y:0};
    $.fn.iscroll = function(options, scrollEndFn, scrollMoveFn){
    	if(this.data('iScrollReady') == null){
			var that = this;
            var options =  $.extend({
            	hideScrollbar:true,
        		bounceLock:true,
        		hScroll:false}, options);
				options.onScrollEnd = function(){
					if(scrollEndFn) {
						scrollEndFn.apply();
						that.bind('onScrollEndFn', scrollEndFn);
					}
					that.triggerHandler('onScrollEnd', [this]);
				};
				options.onScrollMove = scrollMoveFn;	
				arguments.callee.object  = new iScroll(this.get(0), options);
					// NOTE: for some reason in a complex page the plugin does not register
					// the size of the element. This will fix that in the meantime.
				setTimeout(function(scroller){
					scroller.refresh();
				}, 1000, arguments.callee.object);
			this.data('iScrollReady', true);
		}else{
			arguments.callee.object.refresh();
		}
		return arguments.callee.object;
	};
	
	T5.extendInitializers(function(){
		function init(spec) {
			 var currentPage = spec.currentPage;
			 var maxPages = spec.maxPages;
			 var totalRows = spec.totalRows;
			 var refreshFlag = false;
			 var loadFlag = true;
			 var customFn = spec.customFn;
			 var unwrapperFlag = spec.unwrapperFlag;
			 var onScrollEndFn = function() {
				 var that = this;
				 if(refreshFlag) {
					 refreshFlag = false;
					 $.ajax({
							url: spec.refreshLink,
							type: "POST",
							data: 'totalRows=' + totalRows,
							dataType: "json", 
							success: function(jso){
								if(jso.success) {
									maxPages = jso.maxPages;
									totalRows = jso.totalRows;
									currentPage = 0;
									loadFlag = true;
									$("#" + spec.id + ">div>ul").html("");
									$("#" + spec.id).trigger('onScrollEndFn');
								}
							}
					 });
				 } else if(loadFlag) {
					 loadFlag = false;
					 $.ajax({
							url: spec.url,
							type: "POST",
							data: 'page='+ (++currentPage),
							dataType: "json", 
							beforeSend : function(xhr, opts){
						        if(maxPages < currentPage) {
						            xhr.abort();
						        }
						    },						
							success: function(html){
							   html = $.trim( html.content );
							   if ( html ) {
								   if(unwrapperFlag) {
									   $("#" + spec.id + ">div>ul").append( $(html).unwrap().html() );
								   } else {
									   $("#" + spec.id + ">div>ul").append( $(html).unwrap().find('ul').html() );
								   }
		                     	  setTimeout(function(){$("#" + spec.id).iscroll();}, 300);
		                     	  //$("#" + spec.id).refresh();
		                       }
							}
						}); 
				 }
				 if(customFn) {
					 eval(customFn);
				 }
			 };
			 $("#" + spec.id).iscroll(spec.params, onScrollEndFn , function() {
				 if (this.y > 5 && !refreshFlag) {
					 refreshFlag = true;
	             } else if (this.y < 5 && refreshFlag) {
	            	 refreshFlag = false;
	             } else if (this.y < (this.maxScrollY - 5) && !loadFlag) {
	            	 loadFlag = true;
	             } else if (this.y > (this.maxScrollY + 5) && loadFlag) {
	            	 loadFlag = false;
	             }
				 position.x = this.x;
				 position.y = this.y;
			 });
			 if( $('#' + spec.id).length ){
				var hd_height = $('#header').height();
				var ft_height = $('#footer').height();
				$('#' + spec.id).css('height', $(window).height()-hd_height-ft_height );
			 }
			 $('#' + spec.id).bind('onScrollRefreshFn', function() {
				 refreshFlag = true;
				 onScrollEndFn.apply();
			 });
			$('#' + spec.id).show();

		}
		return {
			iscroll : init
		}
	});
	window.iscrollPosition = position;
}) ( jQuery, window );