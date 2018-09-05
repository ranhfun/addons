package com.ranhfun.bpush.services;

import java.util.Map;

public interface BPushService {

	/**
	 * 信息通知
	 * @param apiKey
	 * @param secretKey
	 * @param userId
	 * @param channelId
	 * @param title
	 * @param description
	 * @param customContents
	 * @return 推送成功数量 0表示未成功
	 */
	public int notice(String apiKey, String secretKey, String userId, String channelId, String title, String description, Map<String, String> customContents);
	
	/**
	 * 信息通知
	 * @param userId
	 * @param channelId
	 * @param title
	 * @param description
	 * @param customContents
	 * @return 推送成功数量 0表示未成功
	 */
	public int notice(String userId, String channelId, String title, String description, Map<String, String> customContents);
	
}
