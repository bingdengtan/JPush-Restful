package com.je.push;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.*;
import org.apache.commons.lang.*;
import org.apache.commons.beanutils.*;

@SuppressWarnings("deprecation")
public class JeguangPush {
	public static String PLATFORM_ANDROID = "android";
	public static String PLATFORM_IOS = "ios";
	public static String PLATFORM_ALL = "all";
	
	private static String STR_PUSH_URL = "https://api.jpush.cn/v3/push";
	private static String STR_Encode_Charset ="UTF-8";
	
	private String masterSecret = "d314622d229124c709f025f5";	//the secret key from JPush in your register project
	private String appKey = "a640efcf314f14a3e55b0151";			//the app key from JPush in your register project
	private String platform = "all";
	private boolean apnsProdcution = true;
	private int liveTime = 86400;
	private String[] regIds;
	private String title;
	private String content;
	private String documentUNID;
	private int badge;
	
	public JeguangPush() {

	}

	public JeguangPush(String appkey, String masterSecret) {
		this.appKey = appkey;
		this.masterSecret = masterSecret;
	}
	
	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String[] getRegIds() {
		return regIds;
	}

	public void setRegIds(String[] regIds) {
		this.regIds = regIds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBadge() {
		return badge;
	}

	public void setBadge(int badge) {
		this.badge = badge;
	}
	
	public String getDocumentUNID() {
		return documentUNID;
	}

	public void setDocumentUNID(String documentUNID) {
		this.documentUNID = documentUNID;
	}

	public String push(String[] regIds, String title, String content, int badage, String docUNID) {
		this.setRegIds(regIds);
		this.setTitle(title);
		this.setContent(content);
		this.setBadge(badage);
		this.setDocumentUNID(docUNID);
		
		JSONObject json = this.generateJson();
		
		return this.sendPostRequest(json.toString());
	}

	private JSONObject generateJson() {
		JSONObject json = new JSONObject();
		
		JSONArray platform = new JSONArray();
		platform.add(this.getPlatform());
		
		JSONObject audience = new JSONObject();
		audience.put("registration_id", this.regIds);
		
		JSONObject notification = new JSONObject();
		if(this.getPlatform().equalsIgnoreCase(this.PLATFORM_ANDROID) | this.getPlatform().equalsIgnoreCase(this.PLATFORM_ALL)){
			JSONObject android = new JSONObject();
			android.put("alert", this.getContent());
			JSONObject android_extras = new JSONObject();
			android_extras.put("badge", this.getBadge());
			android_extras.put("unid", this.getDocumentUNID());
			android.put("extras", android_extras);
			
			notification.put("android", android);
		}
		
		if(this.getPlatform().equalsIgnoreCase(this.PLATFORM_IOS) | this.getPlatform().equalsIgnoreCase(this.PLATFORM_ALL)){
			JSONObject ios = new JSONObject();
			ios.put("alert", this.getContent());
			ios.put("sound", "default");
			ios.put("badge", this.getBadge());
			JSONObject ios_extras = new JSONObject();
			ios_extras.put("unid", this.getDocumentUNID());
			ios.put("extras", ios_extras);
			
			notification.put("ios", ios);
		}
		
		JSONObject options = new JSONObject();
		options.put("time_to_live", Integer.valueOf(this.getLiveTime()));
		if(this.getPlatform().equalsIgnoreCase(this.PLATFORM_IOS) | this.getPlatform().equalsIgnoreCase(this.PLATFORM_ALL)){
			options.put("apns_production", this.isApnsProdcution());
		}
		
		json.put("platform", platform);
		json.put("audience", audience);
        json.put("notification", notification);
        json.put("options", options);		
		
		return json;
	}
	
	private String sendPostRequest(String data) {
		HttpPost httpPost = new HttpPost(this.STR_PUSH_URL);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		String result = "";
		
		try {
			StringEntity entity = new StringEntity(data,this.STR_Encode_Charset);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			httpPost.setHeader("Authorization",this.getAuthString());
			response = client.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(),this.STR_Encode_Charset);
		}catch(Exception e) {
			
		}finally {
			client.getConnectionManager().shutdown();
		}
		
		return result;
	}
	
	private String getAuthString() {
		String str = this.appKey + ":" + this.masterSecret;
		byte[] key = str.getBytes();
		Base64 base64Encoder = new Base64();
		String strs = "Basic " + base64Encoder.encodeBase64String(key);
		return strs;
	}

	public boolean isApnsProdcution() {
		return apnsProdcution;
	}

	public void setApnsProdcution(boolean apnsProdcution) {
		this.apnsProdcution = apnsProdcution;
	}

	public int getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(int liveTime) {
		this.liveTime = liveTime;
	}

}
