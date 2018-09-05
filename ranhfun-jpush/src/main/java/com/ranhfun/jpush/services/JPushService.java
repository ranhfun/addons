package com.ranhfun.jpush.services;

import java.util.Map;

import cn.jpush.api.push.PushResult;

public interface JPushService {

	/**
	 * 信息通知
	 * @param apiKey
	 * @param secretKey
	 * @param userId
	 * @param title
	 * @param description
	 * @param customContents
	 * @return PushResult 存储对应返回信息
	 */
	public PushResult notice(String apiKey, String secretKey, String userId, String title, String description, Map<String, String> customContents);
	
	/**
	 * 信息通知
	 * @param userId
	 * @param title
	 * @param description
	 * @param customContents
	 * @return PushResult 存储对应返回信息
	 */
	public PushResult notice(String userId, String title, String description, Map<String, String> customContents);
	
}
