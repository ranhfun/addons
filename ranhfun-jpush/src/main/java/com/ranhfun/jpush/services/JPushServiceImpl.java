package com.ranhfun.jpush.services;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.ranhfun.jpush.JPushConstants;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushServiceImpl implements JPushService {

	@Inject
	@Symbol(JPushConstants.JPUSH_API_KEY)
	private String apiKey;
	
	@Inject
	@Symbol(JPushConstants.JPUSH_SECRET_KEY)
	private String secretKey;
	
	@Inject
	private Logger logger;
	
	public PushResult notice(String apiKey, String secretKey, String userId,
			String title, String description, Map<String, String> customContents) {
		JPushClient jpushClient = new JPushClient(secretKey, apiKey, 3);

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(userId))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                        		.setTitle(title)
                        		.setAlert(description)
                                .addExtras(customContents)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(description)
                                .setSound("happy")
                                .setContentAvailable(true)
                                .addExtras(customContents)
                                .build())
                        .build())
                .build();

        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);
            return result;
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("[JPush Connection error, should retry later]", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("[JPush Should review the error, and fix the request]", e);
            logger.info("JPush HTTP Status: " + e.getStatus());
            logger.info("JPush Error Code: " + e.getErrorCode());
            logger.info("JPush Error Message: " + e.getErrorMessage());
        }
		return null;
	}

	public PushResult notice(String userId, String title,
			String description, Map<String, String> customContents) {
		return notice(apiKey, secretKey, userId, title, description, customContents);
	}
	
}
