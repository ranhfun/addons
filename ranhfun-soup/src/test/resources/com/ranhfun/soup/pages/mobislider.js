/*
**	@Description:	mobile slider js (need jQuery.js iscroll.js)
**	@Author:		cb340081631
**	@Update:		2013-12-10
*/

(function($){
	
	$.fn.extend({
		
		// slider
		listSlider: function(time){
			var $this = $(this);
			var ww_p = $(this).width();
			if( $this.find('ul').hasClass('col2') ){
				var num_p = 2;
			}else if( $this.find('ul').hasClass('col3') ){
				var num_p = 3;
			}else {
				var num_p = 1;
			}
			$this.find('li').css('width', ww_p/num_p);
			var ctrl = '<div class="controller"><a class="prev icon chevron-left" href="javascript:;"></a><a class="next icon chevron-right" href="javascript:;"></a></div><ul class="indicator"><li class="curr">' + 1 + '</li>'
			var num_a = $this.find('li').length;
			var ww_a = $this.find('li').width() * num_a;
			for( var i=1; i<Math.ceil( num_a/num_p ); i++){
				ctrl += '<li>' + (i+1) + '</li>'
			}
			ctrl += '</ul>';
			
			$this.children('.scroller').css('width', ww_a);
			$this.append(ctrl);
			
			var listScroll = new iScroll( String($(this).attr('id')), {
				snap: true,
				momentum: false,
				vScroll: false,
				hScrollbar: false,
				vScrollbar: false,
				onBeforeScrollStart:function(e){
					e.preventDefault && e.preventDefault();
					e.returnValue = false;
					e.stopPropagation && e.stopPropagation();
					return false;
				},
				onScrollEnd: function(){
					$this.find('.indicator li').removeClass('curr');
					$this.find('.indicator li:nth-child(' + (this.currPageX+1) + ')').addClass('curr');
					if( num_a > num_p ){
						if( $this.find('.indicator li:first-child').hasClass('curr') ){
							$this.find('a.prev').hide().end().find('a.next').show();
						}else if( $this.find('.indicator li:last-child').hasClass('curr') ){
							$this.find('a.prev').show().end().find('a.next').hide();
						}else {
							$this.find('a.prev').show().end().find('a.next').show();
						}
					}
				}
			});
			
			if( num_a > num_p ){
				$this
					.find('a.next').show().bind('click',function(){
						listScroll.scrollToPage('next', 0);
						return false;
					}).end()
					.find('a.prev').bind('click',function(){
						listScroll.scrollToPage('prev', 0);
						return false;
					});
			}
			
			if ( time > 0 ){
				setInterval(function(){
					listScroll.scrollToPage('next', 0);
					if( $this.find('.indicator li:last-child').hasClass('curr') ){
						listScroll.scrollToPage(0, 0);
					}
				},time);
			}
		},
		
		// flow
		listFlow: function(time){
			var $this = $(this).find('ul');
			var n = parseInt($(window).width())/2;
			$this.css('left', n).show();
			if( time > 0 ){
				var flow = setInterval(function(){
					$this.css('left', n--)
					if( n == -parseInt($this.width())){
						n = parseInt($(window).width());
					}
				},time);
			}
		},
		
		// drop
		listDrop: function(callback){
			var $this = $(this).children('a');
			$this.bind('click', function(){
				if( $(this).hasClass('open') ){
					$(this).next().hide();
					$(this).removeClass('open');
				}else{
					$(this).addClass('open');
					$(this).next().show();
					if(callback!=null)
					{
					  eval(callback)($this);
					}
				}
				myScroll.refresh();
				return false;
			});
		}
		
	});
	
})(jQuery);
