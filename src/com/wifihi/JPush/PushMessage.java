package com.wifihi.JPush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class PushMessage {
	private static final String appKey="6f45844d5384e36ce6c5ca8f";
	private static final String masterSecret="2e6130b3f092865d5812067c";
//	public static final String TITLE = "Test from API example";
    public static final String ALERT = "牛掰不?";
//  public static final String REGISTRATION_ID = "0900e8d85ef";
    public static JPushClient jpush= null;
    /** 
     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。 
     * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。 
     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。 
     */  
    private static int timeToLive =  60 * 60 * 24; 
    
    public static void main(String[] args) throws APIConnectionException, APIRequestException{
    	
    	jpush = new JPushClient(masterSecret, appKey, timeToLive); 
    
    	sendPush();
    }
    
	public static void sendPush() throws APIConnectionException, APIRequestException{
		
	//	jpush = new JPushClient(masterSecret, appKey, timeToLive); 
		PushPayload payload = buildPushObject_all_all_alert();
		PushResult result = jpush.sendPush(payload);
		System.out.println(result);
		
	}
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(ALERT);
	}
}
