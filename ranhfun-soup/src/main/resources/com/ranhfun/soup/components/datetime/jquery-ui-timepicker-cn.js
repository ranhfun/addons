
/* Catalan translation for the jQuery Timepicker Addon */
/* Written by Sergi Faber */
(function($) {
	$.datepicker.regional['zh-CN'] = {   
		closeText: '关闭',   
		prevText: '&#x3c;上月',   
		nextText: '下月&#x3e;',   
		currentText: '今天',   
		monthNames: ['01月','02月','03月','04月','05月','06月', '07月','08月','09月','10月','11月','12月'],   
		monthNamesShort: ['01月','02月','03月','04月','05月','06月', '07月','08月','09月','10月','11月','12月'],   
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],   
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],   
		dayNamesMin: ['日','一','二','三','四','五','六'],   
		dateFormat: 'yy/mm/dd', firstDay: 1,   
		isRTL: false
	};   
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);   
	
	$.timepicker.regional['cn'] = {
		timeOnlyTitle: '选择时间',
		timeText: '时间',
		hourText: '时',
		minuteText: '钟',
		secondText: '秒',
		millisecText: '毫秒',
		timezoneText: '时区',
		currentText: '现在',
		closeText: '关闭',
		timeFormat: 'hh:mm',
		amNames: ['AM', 'A'],
		pmNames: ['PM', 'P'],
		ampm: false
	};
	$.timepicker.setDefaults($.timepicker.regional['cn']);
})(jQuery);
