package com.boot.org.application.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boot.org.common.HttpConnect;

import net.sf.json.JSONObject;

/**
 * 获取微信accesee通行证。
 * @author xiaoyao
 * @version 2017.9.7
 */
public class GetAccessToken {
	
	private static Logger logger = LoggerFactory.getLogger(GetAccessToken.class);
	

	 public static String accessToken(String appid,String appSecret){
		 try {
			String result = HttpConnect.connectServerGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret);
			JSONObject jsonResult = JSONObject.fromObject(result);
			 return jsonResult.get("access_token").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取微信accesstoken失败");
			return null;
		}
	    }
	 
	 public static void main(String[] args) {
		 System.out.println(GetAccessToken.accessToken("wx9ba43aacecc6959a","6e8d6d19c3ac3b2b4b437dda40e56f1d"));
	}
}
