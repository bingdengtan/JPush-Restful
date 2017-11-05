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

@SuppressWarnings("deprecation")
public class JiguangPush {
	private static String STR_PUSH_URL = "https://api.jpush.cn/v3/push";
	
	private boolean apnsProdcution = true;
	private int liveTime = 86400;
	private String masterSecret = "d314622d229124c709f025f5";
	private String appKey = "a640efcf314f14a3e55b0151";

	public JiguangPush() {

	}

	public JiguangPush(String appkey, String masterSecret) {
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

	public String push() {
		return "";
	}

	private JSONObject generateJson() {
		JSONObject json = new JSONObject();
		
		return json;
	}
	
	private String sendPostRequest(String ReqURL,String data,String encodeCharset) {
		HttpPost httpPost = new HttpPost();
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		String result = "";
		
		try {
			StringEntity entity = new StringEntity(data,encodeCharset);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			httpPost.setHeader("Authorization",this.getAuthString());
			response = client.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(),encodeCharset);
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
