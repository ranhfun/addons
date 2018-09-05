package com.ranhfun.bpush.services;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.slf4j.Logger;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.ranhfun.bpush.BPushConstants;

public class BPushServiceImpl implements BPushService {

	@Inject
	@Symbol(BPushConstants.BPUSH_API_KEY)
	private String apiKey;
	
	@Inject
	@Symbol(BPushConstants.BPUSH_SECRET_KEY)
	private String secretKey;
	
	@Inject
	private Logger logger;
	
	
	public int notice(String apiKey, String secretKey, String userId,
			String channelId, String title, String description,
			Map<String, String> customContents) {
        ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);

        BaiduChannelClient channelClient = new BaiduChannelClient(pair);
        try {
            PushUnicastMessageRequest request = new PushUnicastMessageRequest();
            request.setDeviceType(3); // device_type => 1: web 2: pc 3:android
                                      // 4:ios 5:wp
            request.setChannelId(Long.valueOf(channelId));
            request.setUserId(userId);

            request.setMessageType(1);
            JSONObject jso = new JSONObject();
            jso.put("title", title);
            jso.put("description", description);
            if (customContents.size()>0) {
            	JSONObject jsoCust = new JSONObject();
            	for (Map.Entry<String, String> entry : customContents.entrySet()) {
            		jsoCust.put(entry.getKey(), entry.getValue());
				}
            	jso.put("custom_content", jsoCust);
			}
            request.setMessage(jso.toString());

            // 5. 调用pushMessage接口
            PushUnicastMessageResponse response = channelClient
                    .pushUnicastMessage(request);

            // 6. 认证推送成功
            return response.getSuccessAmount();

        } catch (ChannelClientException e) {
        	logger.error("[BPush notice client fail]", e);
        } catch (ChannelServerException e) {
        	logger.error(String.format("[BPush notice service fail]{request_id: %d, error_code: %d, error_message: %s}}", e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        }
		return -1;
	}

	public int notice(String userId, String channelId, String title,
			String description, Map<String, String> customContents) {
		return notice(apiKey, secretKey, userId, channelId, title, description, customContents);
	}
	
}
