package com.je.push;

import java.io.IOException;

import net.sf.json.JSONObject;

import com.je.push.*;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String[] regIds = {"121c83f76020cb50f2e"};
		
		JeguangPush jePush = new JeguangPush("a640efcf314f14a3e55b0151","d314622d229124c709f025f5");
		jePush.setApnsProdcution(false);
		jePush.setPlatform(jePush.PLATFORM_IOS);
		
		String result = jePush.push(regIds, "EAR Approval", "EAR D20170001 is pending for your approval", 1, "256987");
		JSONObject resData = JSONObject.fromObject(result);
		
	}

}
