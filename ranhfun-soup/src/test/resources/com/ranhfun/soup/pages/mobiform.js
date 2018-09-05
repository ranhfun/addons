/*
**	@Description:	mobile form js
**	@Author:		cb340081631
**	@Update:		2013-12-10
*/

(function($){
	
	$.fn.extend({
		
		// select
		mySelector: function(){
			
			var $sel = $(this).find('select');
			if ( $sel.length ){
				
				if( $sel.get(0).selectedIndex == 0 ){
					var show = '<input type="text" readonly class="sel-txt" placeholder="' + $sel.find('option:selected').text() + '" />';
				}else{
					var show = '<input type="text" readonly class="sel-txt" value="' + $sel.find('option:selected').text() + '" />';
				}
				$(this).append(show);
			
				$(this).bind('click',function(){
					var popup = '<div id="jsMask"></div><div id="jsSelectPop"><div class="scroller"><ul class="sel-list">';
					var num = $sel.find("option").length;
					for( var i=0; i<num; i++){
						popup += '<li>' + $sel.find("option").eq(i).text() + '</li>'
					}
					popup += '</ul>';
					$('body').append(popup);
					
					window.ontouchmove = function(e){
						e.preventDefault && e.preventDefault();
						e.returnValue = false;
						e.stopPropagation && e.stopPropagation();
						return false;
					}
					
					var $pop = $('#jsSelectPop');
					$('#jsMask').show().bind('click',function(){
						$pop.remove();
						$(this).remove();
						window.ontouchmove = null;
					});
					
					$pop.fadeIn().bind('click',function(e){
						var $selected = $(e.target);
						$sel.get(0).selectedIndex = $selected.index()+1;
						$sel.siblings('input')[0].value = $selected.text();
						$pop.remove();
						$('#jsMask').remove();
						window.ontouchmove = null;
					});
					
					if ( $pop.height() > $(window).height()-40 ){
						$pop.css('height', $(window).height()-40);
						var selScroll = new iScroll('jsSelectPop',{
							hideScrollbar:false,
							bounce:false
						});
					}
					
				});
			}
		},
		
		// checkbox
		myCheck: function(){
			var $chk = $(this).find('input');
			if( $chk.attr('type') == 'radio'){
				$(this).bind('click',function(){
					$chk.attr('checked', true);
					$(this).addClass('checked').siblings().removeClass('checked').find('input[type="radio"]').attr('checked', false);
				});
			}else if( $chk.attr('type') == 'checkbox' ){
				if( $chk.attr('checked') == 'checked'){
					$(this).addClass('checked');
				}
				$(this).bind('click',function(){
					$chk.attr('checked', !$chk.attr('checked'));
					if( $chk.attr('checked') == 'checked' ){
						$(this).addClass('checked');
					}else{
						$(this).removeClass('checked');
					}
				});
			}
			return false;
		},
		
		// radio
		myRadio: function(){
			$(this).find('li')
				.has('input:checked').addClass('checked').end()
				.bind('click',function(){
					$(this)
						.addClass('checked')
						.find('input[type="radio"]').attr('checked', true).end()
						.siblings().removeClass('checked').find('input[type="radio"]').attr('checked', false);
				});
				
		},
		
		// drop
		myDrop: function(){
			var $this = $(this).children('a');
			$this.bind('click', function(){
				if( $this.hasClass('open') ){
					$this.next().hide();
					$this.removeClass('open');
				}else{
					$this.addClass('open');
					$this.next().show();
				}
				myScroll.refresh();
			});
		},
		
		// Popup
		myPopup: function(obj){
			$(this).bind('click',function(){
				window.ontouchmove = function(e){
					e.preventDefault && e.preventDefault();
					e.returnValue = false;
					e.stopPropagation && e.stopPropagation();
					return false;
				}
				
				$('body').append('<div id="jsMask"></div>');
				$('#jsMask').css('height', $(document).height()).show().bind('click',function(){
					$(obj).hide();
					$(this).remove();
					window.ontouchmove = null;
				});
				
				$(obj).css('top', $(document).scrollTop()+20).fadeIn()
					.find('.jsClose').bind('click',function(){
						$(obj).hide();
						$('#jsMask').remove();
						window.ontouchmove = null;
					});
				
			});
		}
		
	});
	
})(jQuery);
