package com.ranhfun.bpush.pages;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ranhfun.bpush.services.BPushService;


public class Index {

	@Inject
	private BPushService bPushService;
	
	void onActionFromPush() {
		Map<String, String> custMaps = new HashMap<String, String>();
		custMaps.put("url", "http://www.baidu.com");
		//System.out.println(bPushService.notice("841762292813848222", "3830556384091022063", "测试", "测试描述", custMaps));
		// 用户
		//System.out.println(bPushService.notice("ZlDFMT5jmLhuwVwTjdlZvrZ5","oyjUkiV6TM6X9ZXy4ad6C5PtFeZNTnNc","680122919210329625", "3830556384091022063", "测试", "测试描述", custMaps));
		// 技师
		System.out.println(bPushService.notice("HtEsrlX5UtGUstmIBaBblIum","i4KQ6xuBcCCvfh041qxmugz0gqzg51Pb","983662413067876285", "3830556384091022063", "测试", "测试描述", custMaps));
		// 大区经理
		//System.out.println(bPushService.notice("n45Zr0zhz9MIxG8fhKe4jBDX","YelQHpoXLsPwn5Fmd6quybuIpC4gNwW5","1045898151972165685", "3830556384091022063", "测试", "测试描述", custMaps));
	}
	
}
