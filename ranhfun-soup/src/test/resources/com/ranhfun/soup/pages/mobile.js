/*
**	@Description:	mobile js
**	@Author:		cb340081631
**	@Update:		2013-12-10
*/

/*
	gy:		#708ba1		#556B85
	rd:		#F26175		#E14A59
	og:		#FF8359		#EF6343
	ye:		#F8CD36		#E7B729
	bl:		#5CA7DF		#468BCB
	gn:		#4CD9AA		#3AC48E
	vt:		#9E7AC2		#815DAA
	br:		#CC8952
*/

var myScroll;
$(function(){
	
	// hover点击效果
	$('a').each(function(index, element) {
		$(this).get(0).addEventListener('touchstart', function(){
			var ml = $(this);
			document.body.addEventListener('touchend', function(){
				ml.removeClass('hover');
				return false;
			}, false);
			document.body.addEventListener('touchmove', function(){
				ml.removeClass('hover');
				return false;
			}, false);
			ml.addClass('hover');
		}, false);
	});
	
	if( $('#wrapper').length ){
		var hdh = $('#header').height(),
			fth = $('#footer').height();
		$('#wrapper').height($(window).height()-hdh-fth);
	}
	
});

$(window).load(function(){
	var wFlag = false;
	try {
		if($("#wrapper").iscroll()) {
			wFlag = true;
		}
	} catch(e) {
		// 
	}
	if(!wFlag) {
		// iscroll初始化
		myScroll = new iScroll('wrapper', {
			hideScrollbar:true,
			bounceLock:true,
			hScroll:false
		});	
	}
	
});